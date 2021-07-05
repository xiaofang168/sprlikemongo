package org.sprlikemongo.ql;

import org.sprlikemongo.launch.DbOrder;

import java.util.List;

/**
 * @author fangjie
 * @date Created in 11:04 上午 2021/7/5.
 */
@FunctionalInterface
public interface OrderQl extends DbQl {
    /**
     * 排序集合
     *
     * @return
     */
    List<DbOrder.OrderOperation> list();
}

