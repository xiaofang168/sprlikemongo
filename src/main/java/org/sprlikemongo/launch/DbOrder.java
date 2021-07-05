package org.sprlikemongo.launch;

import org.sprlikemongo.ql.DbQl;
import org.sprlikemongo.symbol.OrderSymbolEnum;

/**
 * @author fangjie
 * @date Created in 11:05 上午 2021/7/5.
 */
public class DbOrder {

    private DbOrder() {

    }

    public static OrderOperation name(String name) {
        return new OrderOperation(name);
    }

    public static class OrderOperation implements DbQl {

        private String name;
        private OrderSymbolEnum orderSymbolEnum;

        private OrderOperation() {
        }

        private OrderOperation(String name) {
            this.name = name;
        }

        public OrderOperation asc() {
            this.orderSymbolEnum = OrderSymbolEnum.ASC;
            return this;
        }

        public OrderOperation desc() {
            this.orderSymbolEnum = OrderSymbolEnum.DESC;
            return this;
        }

        public OrderSymbolEnum getOrderSymbolEnum() {
            return orderSymbolEnum;
        }

        public String getName() {
            return name;
        }
    }

}

