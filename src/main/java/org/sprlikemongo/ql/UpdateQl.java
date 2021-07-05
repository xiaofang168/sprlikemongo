package org.sprlikemongo.ql;

import org.sprlikemongo.launch.DbUpdate;

import java.util.List;

/**
 * @author fangjie
 * @date Created in 11:08 上午 2021/7/5.
 */
@FunctionalInterface
public interface UpdateQl extends DbQl {
    /**
     * 修改字段集合
     *
     * @return
     */
    List<DbUpdate.UpdateOperation> list();
}

