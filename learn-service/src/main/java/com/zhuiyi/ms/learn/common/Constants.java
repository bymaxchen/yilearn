package com.zhuiyi.ms.learn.common;

/**
 * 此类的描述是：
 *
 * @author crazyhu@wezhuiyi.com
 * @date 2018-09-19 18:27
 **/
public class Constants {

    /**
     * yyyy-MM-dd hh:mm:ss 12小时制
     * yyyy-MM-dd HH:mm:ss 24小时制
     */
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     *  全部分类id
     */
    public static final Integer ALL_CATEGORY_ID = 1;

    /**
     *  未分类id
     */
    public static final Integer UNKNOWN_CATEGORY_ID = 2;

    public static Boolean canCategoryDeleted(Integer id) {
        if (id== ALL_CATEGORY_ID || id == UNKNOWN_CATEGORY_ID) {
            return false;
        }
        return true;
    }


}
