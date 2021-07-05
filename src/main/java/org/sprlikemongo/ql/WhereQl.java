package org.sprlikemongo.ql;

import org.sprlikemongo.launch.DbWhere;

import java.util.List;

/**
 * @author fangjie
 * @date Created in 11:08 上午 2021/7/5.
 */
@FunctionalInterface
public interface WhereQl extends DbQl {
    /**
     * 查询条件集合
     *
     * @return
     */
    List<DbWhere.FieldCondition> list();
}


