package org.sprlikemongo.launch;

import org.sprlikemongo.ql.DbQl;

/**
 * @author fangjie
 * @date Created in 11:07 上午 2021/7/5.
 */
public class DbUpdate {

    private DbUpdate() {
    }

    public static UpdateOperation name(String name) {
        return new UpdateOperation(name);
    }

    public static class UpdateOperation implements DbQl {
        private String name;
        private Object value;

        private UpdateOperation() {
        }

        private UpdateOperation(String name) {
            this.name = name;
        }

        public UpdateOperation eq(Object value) {
            this.value = value;
            return this;
        }

        public String getName() {
            return name;
        }

        public Object getValue() {
            return value;
        }
    }

}

