package com.zhuiyi.ms.learn.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @ClassName HttpContextUtils
 * @Description TODO 获取上下文信息
 * @Author xanderwang@wezhuiyi.com
 * @Date 2019/05/08 12:03
 **/
public class HttpContextUtils {

    private static ThreadLocal<String> localRequest = new ThreadLocal<>();

    /**
     * @Description: TODO 获取当前HttpServletRequest
     * @param
     * @return: HttpServletRequest
     * @Date: 2019/5/9
     **/
    public static HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;

    }

    /**
     * @Description: TODO 获取当前HttpSession
     * @param
     * @return: HttpSession
     * @Date: 2019/5/9
     **/
    public static HttpSession getSession() {
        HttpSession session = getRequest().getSession();
        return session;
    }

    public static String getDsFromSession() {
        return getSession().getAttribute("dataSourceName").toString();
    }

    public static void setDs(String ds) {
        localRequest.set(ds);
    }

    public static String getDs() {
        return localRequest.get();
    }
}