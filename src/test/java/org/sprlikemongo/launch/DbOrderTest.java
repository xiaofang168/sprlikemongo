package org.sprlikemongo.launch;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author fangjie
 * @date Created in 3:30 下午 2021/7/5.
 */
public class DbOrderTest {

    @Test
    public void name() {
        DbOrder.OrderOperation id = DbOrder.name("_id").asc();
        Assert.assertEquals(id.getName(), "_id");
        Assert.assertEquals(id.getOrderSymbolEnum().name(), "ASC");
    }

}

