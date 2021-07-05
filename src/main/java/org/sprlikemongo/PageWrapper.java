package org.sprlikemongo;

import java.io.Serializable;
import java.util.List;

/**
 * @author fangjie
 * @date Created in 11:14 上午 2021/7/5.
 */
public class PageWrapper<T> implements Serializable {

    public static final int PAGE_SIZE_DEFAULT = 10;

    private List<T> content;

    private Integer pageIndex;

    private Integer pageSize;

    private Long totalPage;

    private Long total;

    /**
     * id,用于排序分页
     */
    private Long id;

    /**
     * 时间戳
     */
    private Long timestamp;

    private Integer offset;

    private boolean hasNext;

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        this.setTotalPage();
    }

    public Long getTotalPage() {
        return totalPage;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
        this.setTotalPage();
    }

    private void setTotalPage() {
        if (this.total == null || pageSize == null || pageSize == 0) {
            return;
        }
        this.totalPage = total / pageSize;
        if (total % pageSize != 0) {
            ++totalPage;
        }
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getOffset() {
        return offset;
    }

    public Boolean getHasNext() {
        return hasNext;
    }

    public void setHasNext(Boolean hasNext) {
        this.hasNext = hasNext;
    }

    public PageWrapper() {
    }

    public PageWrapper(Integer pageIndex, Integer pageSize, int pageSizeDefault) {
        this.init(pageIndex, pageSize, pageSizeDefault);
    }

    public PageWrapper(Integer pageIndex, Integer pageSize) {
        this.init(pageIndex, pageSize, PAGE_SIZE_DEFAULT);
    }

    private void init(Integer pageIndex, Integer pageSize, int pageSizeDefault) {
        if (pageIndex == null || pageIndex < 1) {
            pageIndex = 1;
        }
        if (pageSize == null || pageSize <= 0) {
            pageSize = pageSizeDefault;
        }
        this.offset = (pageIndex - 1) * pageSize;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

}

