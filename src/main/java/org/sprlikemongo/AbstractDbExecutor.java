package org.sprlikemongo;

import org.sprlikemongo.ql.DbQl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author fangjie
 * @date Created in 2:38 下午 2021/7/5.
 */
abstract class AbstractDbExecutor implements DbExecutor {
    @Override
    public <T> PageWrapper<T> search(int page, int size, Class<T> t, DbQl... dbQls) {
        return search(page, size, t, null, dbQls);
    }

    @Override
    public <T> PageWrapper<T> search(int page, int size, Class<T> t, Consumer<PageWrapper<T>> pageWrapperConsumer, DbQl... dbQls) {
        PageWrapper<T> pageWrapper = new PageWrapper(page, size);
        if (pageWrapperConsumer != null) {
            pageWrapperConsumer.accept(pageWrapper);
        }
        // 是否需要再次统计
        if (pageWrapper.getTotal() == null || pageWrapper.getTotal() <= 0) {
            long total = count(t, dbQls);
            pageWrapper.setTotal(total);
            // id深度分页计算,兼容未传total参数
            if (pageWrapper.getId() != null && pageWrapper.getId() > 0) {
                // total未传,id有传
                pageWrapper.setHasNext(total > size);
            } else {
                // id,total都未传
                pageWrapper.setHasNext(pageWrapper.getTotal() > page * size);
            }
        } else {
            // total通过透传
            pageWrapper.setHasNext(pageWrapper.getTotal() > page * size);
        }
        // 没有数据不再查询数据库
        if (pageWrapper.getTotal() <= 0) {
            pageWrapper.setHasNext(false);
            pageWrapper.setContent(new ArrayList<>());
            return pageWrapper;
        }
        List<T> list = list(pageWrapper.getOffset(), size, t, dbQls);
        pageWrapper.setContent(list);
        return pageWrapper;
    }
}

