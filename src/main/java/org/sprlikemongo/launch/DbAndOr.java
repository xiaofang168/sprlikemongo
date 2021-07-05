package org.sprlikemongo.launch;

import org.sprlikemongo.ql.DbQl;
import org.sprlikemongo.ql.WhereQl;

/**
 * @author fangjie
 * @date Created in 4:37 下午 2021/7/1.
 */
public class DbAndOr {
    /**
     * 或者关系
     *
     * @param whereQls
     * @return
     */
    public static AndOrOperator operator(WhereQl... whereQls) {
        return new AndOrOperator(whereQls);
    }

    public static class AndOrOperator implements DbQl {

        private WhereQl[] whereQls;

        private AndOrOperator() {
        }

        private AndOrOperator(WhereQl... whereQls) {
            this.whereQls = whereQls;
        }

        public WhereQl[] getWhereQls() {
            return whereQls;
        }
    }
}
