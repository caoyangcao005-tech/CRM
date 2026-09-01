package common;

import java.util.List;

public class PageResult<T> {
    private final long total;
    private final int page;
    private final int pageSize;
    private final int totalPages;
    private final List<T> items;

    public PageResult(long total, int page, int pageSize, List<T> items) {
        this.total = total;
        this.page = page;
        this.pageSize = pageSize;
        this.totalPages = total == 0 ? 0 : (int) ((total + pageSize - 1) / pageSize);
        this.items = items;
    }

    public long getTotal() {
        return total;
    }

    public int getPage() {
        return page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public List<T> getItems() {
        return items;
    }
}
