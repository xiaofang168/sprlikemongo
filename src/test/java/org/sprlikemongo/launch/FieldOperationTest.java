package org.sprlikemongo.launch;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author fangjie
 * @date Created in 3:30 下午 2021/7/5.
 */
public class FieldOperationTest {

    @Test
    public void EQ() {
        DbWhere.FieldCondition eq = DbWhere.name("title").eq("张三");
        Assert.assertEquals(eq.getConditions().get(0).getWhereSymbolEnum().name(), "EQ");
        Assert.assertEquals(eq.getConditions().get(0).getValue(), "张三");
    }

}

