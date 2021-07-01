package org.sprlikemongo.symbol;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;

/**
 * 排序枚举类
 *
 * @author fangjie
 * @date Created in 下午18:10 20/03/20.
 */
public enum OrderSymbolEnum {
    ASC {
        @Override
        public Query join(Query query, String name) {
            return query.with(new Sort(Sort.Direction.ASC, name));
        }
    }, DESC {
        @Override
        public Query join(Query query, String name) {
            return query.with(new Sort(Sort.Direction.DESC, name));
        }
    };

    public abstract Query join(Query query, String name);
}
