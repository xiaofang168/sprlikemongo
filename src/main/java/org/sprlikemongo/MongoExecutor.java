package org.sprlikemongo;

import com.mongodb.WriteResult;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.sprlikemongo.launch.DbAndOr;
import org.sprlikemongo.launch.DbOrder;
import org.sprlikemongo.launch.DbUpdate;
import org.sprlikemongo.launch.DbWhere;
import org.sprlikemongo.ql.DbQl;
import org.sprlikemongo.ql.UpdateQl;
import org.sprlikemongo.ql.WhereQl;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author fangjie
 * @date Created in 2:54 下午 2021/7/5.
 */
@Component
public class MongoExecutor extends AbstractDbExecutor {
    @Resource
    private MongoTemplate mongoTemplate;

    /**
     * 获取where查询语句
     *
     * @param dbQls
     * @return
     */
    private List<DbQl> getDbWhere(DbQl... dbQls) {
        return Arrays.stream(dbQls)
                .filter(e -> e instanceof DbWhere.FieldCondition)
                .collect(Collectors.toList());
    }

    /**
     * 获取或者查询语句
     *
     * @param dbQls
     * @return
     */
    private List<DbQl> getDbAndOr(DbQl... dbQls) {
        return Arrays.stream(dbQls)
                .filter(e -> e instanceof DbAndOr.AndOrOperator)
                .collect(Collectors.toList());
    }


    /**
     * 获取查询Criteria(and or)
     *
     * @param dbQls 数组
     * @return
     */
    private Criteria getCriteria(DbQl... dbQls) {
        Criteria criteria = getSingle(dbQls);
        // 追加and or
        List<DbQl> dbAndOr = getDbAndOr(dbQls);
        // and or为空返回
        if (CollectionUtils.isEmpty(dbAndOr)) {
            return criteria;
        }
        List<Criteria> andCriteriaList = new ArrayList<>();
        // 处理and or情况
        for (DbQl dbQl : dbAndOr) {
            // and 连接条件
            Criteria andCriteria = new Criteria();
            List<Criteria> orCriteriaList = new ArrayList<>();
            WhereQl[] whereQls = ((DbAndOr.AndOrOperator) dbQl).getWhereQls();
            for (WhereQl whereQl : whereQls) {
                orCriteriaList.add(getSingle(whereQl.list()));
            }
            if (whereQls.length <= 1) {
                // 变换and查询
                andCriteria = orCriteriaList.get(0);
            } else {
                // 组合or条件
                andCriteria.orOperator(orCriteriaList.toArray(new Criteria[orCriteriaList.size()]));
            }
            andCriteriaList.add(andCriteria);
        }
        // 组合and条件
        criteria.andOperator(andCriteriaList.toArray(new Criteria[andCriteriaList.size()]));
        return criteria;
    }

    /**
     * 获取查询Criteria(单一,不包含or)
     *
     * @param dbQls 数组
     * @return
     */
    private Criteria getSingle(DbQl... dbQls) {
        List<DbQl> collect = getDbWhere(dbQls);
        DbQl[] fieldConditions = collect.toArray(new DbQl[collect.size()]);
        Criteria criteria = new Criteria();
        for (int i = 0; i < fieldConditions.length; i++) {
            DbWhere.FieldCondition fieldCondition = (DbWhere.FieldCondition) fieldConditions[i];
            if (i == 0) {
                criteria = Criteria.where(fieldCondition.getName());
            } else {
                criteria = criteria.and(fieldCondition.getName());
            }
            for (DbWhere.Condition condition : fieldCondition.getConditions()) {
                condition.getWhereSymbolEnum().join(criteria, condition.getValue());
            }
        }
        return criteria;
    }

    /**
     * 获取查询Criteria(单一,不包含or)
     *
     * @param fieldConditions 条件集合
     * @return
     */
    private Criteria getSingle(List<DbWhere.FieldCondition> fieldConditions) {
        return getSingle(fieldConditions.toArray(new DbQl[fieldConditions.size()]));
    }

    /**
     * 通过集合获取条件
     *
     * @param fieldConditions 集合
     * @return
     */
    private Criteria getCriteria(List<DbWhere.FieldCondition> fieldConditions) {
        DbQl[] dbQls = new DbQl[fieldConditions.size()];
        for (int i = 0; i < fieldConditions.size(); i++) {
            dbQls[i] = fieldConditions.get(i);
        }
        return getCriteria(dbQls);
    }

    /**
     * 获取修改语句
     *
     * @param dbQls 数组
     * @return
     */
    private Update getUpdate(DbQl... dbQls) {
        Update update = new Update();
        Arrays.stream(dbQls)
                .filter(e -> e instanceof DbUpdate.UpdateOperation)
                .forEach(e -> {
                    DbUpdate.UpdateOperation updateOperation = (DbUpdate.UpdateOperation) e;
                    update.set(updateOperation.getName(), updateOperation.getValue());
                });
        return update;
    }

    /**
     * 通过集合获取修改
     *
     * @param updateOperations 集合
     * @return
     */
    private Update getUpdate(List<DbUpdate.UpdateOperation> updateOperations) {
        DbQl[] dbQls = new DbQl[updateOperations.size()];
        for (int i = 0; i < updateOperations.size(); i++) {
            dbQls[i] = updateOperations.get(i);
        }
        return getUpdate(dbQls);
    }

    /**
     * 排序处理
     *
     * @param query
     * @param dbQls
     */
    private void sort(Query query, DbQl... dbQls) {
        Arrays.stream(dbQls)
                .filter(e -> e instanceof DbOrder.OrderOperation)
                .forEach(e -> {
                    DbOrder.OrderOperation orderOperation = (DbOrder.OrderOperation) e;
                    orderOperation.getOrderSymbolEnum().join(query, orderOperation.getName());
                });
    }

    @Override
    public Object get(Class t, long id) {
        return mongoTemplate.findById(id, t);
    }

    @Override
    public Object get(Class t, DbQl... dbQls) {
        Criteria criteria = getCriteria(dbQls);
        Query query = Query.query(criteria);
        sort(query, dbQls);
        return mongoTemplate.findOne(query, t);
    }

    @Override
    public long count(Class t, DbQl... dbQls) {
        Criteria criteria = getCriteria(dbQls);
        Query query = Query.query(criteria);
        return mongoTemplate.count(query, t);
    }

    @Override
    public <T> List<T> list(int offset, int limit, Class<T> t, DbQl... dbQls) {
        Criteria criteria = getCriteria(dbQls);
        Query query = Query.query(criteria);
        query.skip(offset).limit(limit);
        sort(query, dbQls);
        return mongoTemplate.find(query, t);
    }

    @Override
    public boolean del(Class t, DbQl... dbQls) {
        Criteria criteria = getCriteria(dbQls);
        Query query = Query.query(criteria);
        WriteResult writeResult = mongoTemplate.remove(query, t);
        return writeResult.getN() > 0;
    }

    @Override
    public boolean exists(Class t, DbQl... dbQls) {
        Criteria criteria = getCriteria(dbQls);
        Query query = Query.query(criteria);
        return mongoTemplate.exists(query, t);
    }

    @Override
    public int update(Class t, DbQl... dbQls) {
        Assert.notEmpty(getDbWhere(dbQls), "查询条件语句不能为空");

        Criteria criteria = getCriteria(dbQls);
        Query query = Query.query(criteria);
        Update update = getUpdate(dbQls);
        WriteResult writeResult = mongoTemplate.updateMulti(query, update, t);
        return writeResult.getN();
    }

    @Override
    public int update(Class t, WhereQl whereQl, UpdateQl updateQl) {
        // 修改条件语句不能为空
        Assert.notEmpty(whereQl.list(), "查询条件语句不能为空");

        Criteria criteria = getCriteria(whereQl.list());
        Query query = Query.query(criteria);
        Update update = getUpdate(updateQl.list());
        WriteResult writeResult = mongoTemplate.updateMulti(query, update, t);
        return writeResult.getN();
    }

    @Override
    public String getIdName() {
        return "_id";
    }

    @Override
    public <T> void save(T t) {
        mongoTemplate.save(t);
    }

    @Override
    public <T> void save(List<T> list) {
        mongoTemplate.insertAll(list);
    }
}

