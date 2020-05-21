package com.zhuiyi.ms.learn.common;

/**
 * 分页
 *
 * @author crazyhu@wezhuiyi.com
 * @date 2018-08-22 14:41
 **/
public class Page {

    private int offset = 0;

    private int pageSize = 10;

    public static Page toPage(Integer pageNo, Integer pageSize) {
        Page page = new Page();
        if (pageNo == null || pageNo < 1) {
            return page;
        }
        if (pageSize < 1) {
            return page;
        }
        page.setOffset((pageNo - 1) * pageSize);
        page.setPageSize(pageSize);
        return page;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
