package com.zhuiyi.ms.learn.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author rodbate
 * @Title: RemoteProxyFactory
 * @ProjectName learn
 * @Description: TODO
 * @date 2019/2/2620:23
 */
public class RemoteProxyFactory implements InvocationHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RemoteProxyFactory.class);

    private final static int RETRY_TIMES = 2;

    /**
     *  维护一个目标对象
     */
    private final Object target;
    public RemoteProxyFactory(Object target){
        this.target = target;
    }

    /**
     *  给目标对象生成代理对象,获取动态代理
     */
    public static Object getProxyInstance(Object realSubject){
        //代理哪个真实对象，就把该对象传进去，最后通过该真实对象调用其方法
        InvocationHandler handler = new RemoteProxyFactory(realSubject);
        return Proxy.newProxyInstance(handler.getClass().getClassLoader(),
                realSubject.getClass().getInterfaces(), handler);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        int times = 0;
        while(times < RETRY_TIMES){
            try{
                LOGGER.info("try " + times + " remote rest voiceAuth");
                return method.invoke(target,args);
            }catch (Exception ex){
                times++;
                if(times >= RETRY_TIMES){
                    throw new RuntimeException(ex);
                }
            }
        }
        return null;
    }

}
