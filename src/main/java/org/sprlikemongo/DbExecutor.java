package org.sprlikemongo;

import org.sprlikemongo.ql.DbQl;
import org.sprlikemongo.ql.UpdateQl;
import org.sprlikemongo.ql.WhereQl;

import java.util.List;
import java.util.function.Consumer;

/**
 * 数据库操作接口
 *
 * @author fangjie
 * @date Created in 11:11 上午 2021/7/5.
 */
public interface DbExecutor {
    /**
     * 获取id名称,譬如:MongoDB和MySQL("_id","id")
     *
     * @return
     */
    String getIdName();

    /**
     * 统计
     *
     * @param t
     * @param dbQls
     * @return
     */
    <T> long count(Class<T> t, DbQl... dbQls);

    /**
     * 获取单个对象
     *
     * @param t
     * @param dbQls
     * @param <T>
     * @return
     */
    <T> T get(Class<T> t, DbQl... dbQls);


    /**
     * 通过id查询返回唯一对象
     *
     * @param t
     * @param id  主键
     * @param <T>
     * @return
     */
    <T> T get(Class<T> t, long id);

    /**
     * 列表
     *
     * @param offset
     * @param limit
     * @param t
     * @param dbQls
     * @return
     */
    <T> List<T> list(int offset, int limit, Class<T> t, DbQl... dbQls);

    /**
     * 分页
     *
     * @param page
     * @param size
     * @param t
     * @param dbQls
     * @return
     */
    <T> PageWrapper<T> search(int page, int size, Class<T> t, DbQl... dbQls);

    /**
     * 分页,只统计一次,提高查询效率,一般用户深度分页
     *
     * @param page
     * @param size
     * @param t
     * @param pageWrapperConsumer 修正分页参数
     * @param dbQls
     * @param <T>
     * @return
     */
    <T> PageWrapper<T> search(int page, int size, Class<T> t, Consumer<PageWrapper<T>> pageWrapperConsumer, DbQl... dbQls);

    /**
     * 删除
     *
     * @param t
     * @param dbQls
     * @return
     */
    <T> boolean del(Class<T> t, DbQl... dbQls);

    /**
     * 是否存在
     *
     * @param t
     * @param dbQls
     * @param <T>
     * @return
     */
    <T> boolean exists(Class<T> t, DbQl... dbQls);

    /**
     * 修改
     *
     * @param t
     * @param dbQls
     * @param <T>
     * @return
     */
    <T> int update(Class<T> t, DbQl... dbQls);

    /**
     * 修改
     *
     * @param t
     * @param whereQl  条件函数
     * @param updateQl 修改函数
     * @param <T>
     * @return
     */
    <T> int update(Class<T> t, WhereQl whereQl, UpdateQl updateQl);

    /**
     * 保存
     *
     * @param t
     * @param <T>
     */
    <T> void save(T t);


    /**
     * 批量保存
     *
     * @param list
     * @param <T>
     */
    <T> void save(List<T> list);
}

