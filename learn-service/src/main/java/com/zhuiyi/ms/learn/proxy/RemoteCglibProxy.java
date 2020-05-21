package com.zhuiyi.ms.learn.proxy;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author rodbate
 * @Title: RemoteCglibProxy
 * @ProjectName learn
 * @Description: TODO
 * @date 2019/2/2711:05
 */
public class RemoteCglibProxy implements MethodInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(RemoteCglibProxy.class);

    private final static int RETRY_TIMES = 2;

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        int times = 0;

        while (times < RETRY_TIMES) {
            try {
                //通过代理子类调用父类的方法
                LOGGER.info("try " + times + " remote rest voiceAuth");
                return methodProxy.invokeSuper(o, objects);
            } catch (Exception e) {
                times++;
                if (times >= RETRY_TIMES) {
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }

    /**
     * 获取代理类
     * @param clazz 类信息
     * @return 代理类结果
     */
    public Object getProxy(Class clazz){
        Enhancer enhancer = new Enhancer();
        //目标对象类
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        //通过字节码技术创建目标对象类的子类实例作为代理
        return enhancer.create();
    }

}
