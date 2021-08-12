package org.sprlikemongo;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.sprlikemongo.entity.User;
import org.sprlikemongo.launch.DbAndOr;
import org.sprlikemongo.launch.DbOrder;
import org.sprlikemongo.launch.DbUpdate;
import org.sprlikemongo.launch.DbWhere;
import org.sprlikemongo.ql.UpdateQl;
import org.sprlikemongo.ql.WhereQl;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fangjie
 * @date Created in 3:29 下午 2021/7/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestServiceConfig.class})
public class DbExecutorTst {

    @Resource
    private DbExecutor mongoExecutor;

    @Test
    public void save() {
        User user = new User();
        user.setId(1L);
        mongoExecutor.save(user);
    }

    @Test
    public void get() {
        User user = mongoExecutor.get(User.class, DbWhere.name("_id").eq(1));
        System.out.println(">>>>>" + user);
    }

    @Test
    public void count() {
        long count = mongoExecutor.count(User.class);
        System.out.println("count: " + count);
    }

    @Test
    public void list() {
        List<User> list = mongoExecutor.list(0, 20, User.class);
        System.out.println(">>>>>>" + list);
    }

    @Test
    public void search() {
        PageWrapper<User> result = mongoExecutor.search(1, 10, User.class,
                DbWhere.name("adCode").eq("1112"), DbWhere.name("cityKey").eq("10010"));
        System.out.println(">>>>>>" + result);
    }

    @Test
    public void searchOrder() {
        PageWrapper<User> result = mongoExecutor.search(1, 10, User.class, DbOrder.name("_id").asc());
        System.out.println(">>>>>>" + result);
    }

    @Test
    public void searchOrderDesc() {
        PageWrapper<User> result = mongoExecutor.search(1, 10, User.class, DbOrder.name("_id").desc());
        System.out.println(">>>>>>" + result);
    }

    @Test
    public void searchByList() {
        List<DbWhere.FieldCondition> list = new ArrayList();
        DbWhere.FieldCondition adCode = DbWhere.name("adCode").eq("1112");
        DbWhere.FieldCondition cityKey = DbWhere.name("cityKey").eq("1112");
        list.add(adCode);
        list.add(cityKey);
        PageWrapper<User> result = mongoExecutor.search(1, 10,
                User.class, list.toArray(new DbWhere.FieldCondition[list.size()]));
        System.out.println(">>>>>>" + result);
    }

    @Test
    public void del() {
        mongoExecutor.del(User.class, DbWhere.name("_id").eq(2));
    }

    @Test
    public void exists() {
        boolean r = mongoExecutor.exists(User.class, DbWhere.name("_id").eq(3));
        System.out.println(">>>" + r);
    }

    @Test
    public void update() {
        int n = mongoExecutor.update(User.class, DbWhere.name("_id").eq(3), DbUpdate.name("birthday").eq("2000-10-1"));
        System.out.println(">>>" + n);
    }

    @Test
    public void updateByFun() {
        int n = mongoExecutor.update(User.class, (WhereQl) () -> Lists.newArrayList(DbWhere.name("_id").eq(3)),
                (UpdateQl) () -> Lists.newArrayList(DbUpdate.name("birthday").eq("2000-10-2")));
        System.out.println(">>>" + n);
    }

    @Test
    public void or() {
        List<User> list = mongoExecutor.list(0, 10, User.class,
                DbWhere.name("adCode").eq(1111),
                DbAndOr.operator(() -> Lists.newArrayList(DbWhere.name("_id").eq(3))),
                DbAndOr.operator(() -> Lists.newArrayList(DbWhere.name("_id").eq(3),
                        DbWhere.name("adCode").eq(1111)),
                        () -> Lists.newArrayList(DbWhere.name("_id").eq(4))),
                DbAndOr.operator(() -> Lists.newArrayList(DbWhere.name("_id").eq(3),
                        DbWhere.name("adCode").eq(1111)),
                        () -> Lists.newArrayList(DbWhere.name("_id").eq(4))));
        System.out.println(">>>" + list);
    }

    @Test
    public void andOr() {
        List<User> list = mongoExecutor.list(0, 10, User.class,
                DbAndOr.operator(() -> Lists.newArrayList(DbWhere.name("_id").eq(3))),
                DbAndOr.operator(() -> Lists.newArrayList(DbWhere.name("_id").eq(3),
                        DbWhere.name("adCode").eq(1111)),
                        () -> Lists.newArrayList(DbWhere.name("_id").eq(4))));
        System.out.println(">>>" + list);
    }


    @Test
    public void batchSave() {
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        List<User> list = Lists.newArrayList(user1, user2, user3);
        mongoExecutor.save(list);
    }
}

