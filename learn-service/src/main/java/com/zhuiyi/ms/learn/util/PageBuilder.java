package com.zhuiyi.ms.learn.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhuiyi.ms.learn.entity.bo.ResponsePage;

import java.util.ArrayList;

public class PageBuilder {
    public static <T> ResponsePage buildResponsePage(T data, int count){
        ResponsePage<T> resultResponsePage = new ResponsePage<>();
        resultResponsePage.setCount(count);
        resultResponsePage.setRows(data);
        return resultResponsePage;
    }

    public static <T> ResponsePage buildResponsePage(T data, long count){
        return buildResponsePage(data, (int) count);
    }

    public static <T> ResponsePage buildEmptyResponsePage() {
        return buildResponsePage(new ArrayList<>(), 0);
    }

    /*
  将mybatis-plus的IPage转换成成系统里的page
 */
    public static ResponsePage buildResponsePage(IPage page) {
        return buildResponsePage(page.getRecords(), (int) page.getTotal());
    }

    public static Page getMaxPage() {
        return new Page(1, Integer.MAX_VALUE);
    }
}
