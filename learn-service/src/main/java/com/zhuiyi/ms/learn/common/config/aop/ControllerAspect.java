package com.zhuiyi.ms.learn.common.config.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhuiyi.ms.learn.dto.SessionContextHolder;
import com.zhuiyi.ms.learn.dto.response.BaseResponse;
import com.zhuiyi.ms.learn.enums.MsgCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.UUID;

/**
 * Controller后置处理，获取返回值，用于打印日志
 *
 * @author crazyhu@wezhuiyi.com
 * @date 2018-07-23 11:20
 **/

@Configuration
@Aspect
@Slf4j
public class ControllerAspect {

    private final String controllerPoint = "execution(* com.zhuiyi.ms.*.controller.*.*(..)) && ! execution(* com.zhuiyi.ms.*.controller.TAudioController.download(..)) ";

    private static Logger logger = LoggerFactory.getLogger(ControllerAspect.class);

    @Autowired
    private ObjectMapper mapper;

    @Pointcut(controllerPoint)
    public void executeService() {
    }

    @Before("executeService()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        SessionContextHolder.setRequestId(String.valueOf(UUID.randomUUID()));
        // 记录下请求内容
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "."
                + joinPoint.getSignature().getName());

    }

    @AfterReturning(returning = "response", pointcut = "executeService()")
    public void doAfterReturning(Object response) throws JsonProcessingException {
        String result = mapper.writeValueAsString(response);
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        request.setAttribute("responseData", result);
    }

}
