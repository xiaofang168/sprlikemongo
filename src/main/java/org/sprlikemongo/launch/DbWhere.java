package org.sprlikemongo.launch;

import org.sprlikemongo.ql.DbQl;
import org.sprlikemongo.symbol.WhereSymbolEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据操作where条件
 *
 * @author fangjie
 * @date Created in 4:38 下午 2021/7/1.
 */
public class DbWhere {
    private DbWhere() {

    }

    public static FieldOperation name(String name) {
        return new FieldOperation(name);
    }

    public static class FieldOperation {

        private String name;

        private FieldOperation() {
        }

        private FieldOperation(String name) {
            this.name = name;
        }

        public FieldCondition eq(Object value) {
            FieldCondition fieldCondition = new FieldCondition(name, WhereSymbolEnum.EQ, value);
            return fieldCondition;
        }

        public FieldCondition neq(Object value) {
            FieldCondition fieldCondition = new FieldCondition(name, WhereSymbolEnum.NEQ, value);
            return fieldCondition;
        }

        public FieldCondition gte(Object value) {
            FieldCondition fieldCondition = new FieldCondition(name, WhereSymbolEnum.GTE, value);
            return fieldCondition;
        }

        public FieldCondition lte(Object value) {
            FieldCondition fieldCondition = new FieldCondition(name, WhereSymbolEnum.LTE, value);
            return fieldCondition;
        }

        public FieldCondition in(Object value) {
            FieldCondition fieldCondition = new FieldCondition(name, WhereSymbolEnum.IN, value);
            return fieldCondition;
        }

        public FieldCondition nin(Object value) {
            FieldCondition fieldCondition = new FieldCondition(name, WhereSymbolEnum.NIN, value);
            return fieldCondition;
        }

        public FieldCondition like(Object value) {
            FieldCondition fieldCondition = new FieldCondition(name, WhereSymbolEnum.LIKE, value);
            return fieldCondition;
        }

        public FieldCondition exists(Object value) {
            FieldCondition fieldCondition = new FieldCondition(name, WhereSymbolEnum.EXISTS, value);
            return fieldCondition;
        }
    }

    public static class FieldCondition implements DbQl {
        private String name;
        private List<Condition> conditions;

        public FieldCondition(String name, WhereSymbolEnum whereSymbolEnum, Object value) {
            List conditions = new ArrayList();
            conditions.add(new Condition(whereSymbolEnum, value));
            this.name = name;
            this.conditions = conditions;
        }

        public String getName() {
            return name;
        }

        public List<Condition> getConditions() {
            return conditions;
        }

        public FieldCondition gte(Object value) {
            this.conditions.add(new Condition(WhereSymbolEnum.GTE, value));
            return this;
        }

        public FieldCondition lte(Object value) {
            this.conditions.add(new Condition(WhereSymbolEnum.LTE, value));
            return this;
        }
    }

    public static class Condition {
        private WhereSymbolEnum whereSymbolEnum;
        private Object value;

        public Condition(WhereSymbolEnum whereSymbolEnum, Object value) {
            this.whereSymbolEnum = whereSymbolEnum;
            this.value = value;
        }

        public WhereSymbolEnum getWhereSymbolEnum() {
            return whereSymbolEnum;
        }

        public Object getValue() {
            return value;
        }
    }
}

