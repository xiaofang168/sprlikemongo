package org.sprlikemongo.symbol;

import org.springframework.data.mongodb.core.query.Criteria;

import java.util.Collection;

/**
 * 条件枚举类
 *
 * @author fangjie
 * @date Created in 下午18:10 20/03/20.
 */
public enum WhereSymbolEnum {
    GTE {
        @Override
        public Criteria join(Criteria criteria, Object value) {
            return criteria.gte(value);
        }
    }, LTE {
        @Override
        public Criteria join(Criteria criteria, Object value) {
            return criteria.lte(value);
        }

    }, EQ {
        @Override
        public Criteria join(Criteria criteria, Object value) {
            return criteria.is(value);
        }
    }, NEQ {
        @Override
        public Criteria join(Criteria criteria, Object value) {
            return criteria.ne(value);
        }
    }, IN {
        @Override
        public Criteria join(Criteria criteria, Object value) {
            return criteria.in((Collection<?>) value);
        }
    }, NIN {
        @Override
        public Criteria join(Criteria criteria, Object value) {
            return criteria.nin((Collection<?>) value);
        }
    }, EXISTS {
        @Override
        public Criteria join(Criteria criteria, Object value) {
            return criteria.exists((Boolean) value);
        }
    }, LIKE {
        @Override
        public Criteria join(Criteria criteria, Object value) {
            return criteria.regex((String) value);
        }
    };

    public abstract Criteria join(Criteria criteria, Object value);
}
