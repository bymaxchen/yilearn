package com.zhuiyi.ms.learn.common;

import com.baomidou.dynamic.datasource.processor.DsProcessor;
import com.zhuiyi.ms.learn.util.HttpContextUtils;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class CustomDsProcessor extends DsProcessor {

    /**
     * session开头
     */
    private static final String SESSION_PREFIX = "#session";

    @Override
    public boolean matches(String key) {
        return key.startsWith(SESSION_PREFIX);
    }

    @Override
    public String doDetermineDatasource(MethodInvocation invocation, String key) {
        if (RequestContextHolder.getRequestAttributes() == null) {
            return HttpContextUtils.getDs();
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if (request.getSession().getAttribute(key.substring(9)) == null) {
            return "yiteach_base";
        }
        return request.getSession().getAttribute(key.substring(9)).toString();
    }
}