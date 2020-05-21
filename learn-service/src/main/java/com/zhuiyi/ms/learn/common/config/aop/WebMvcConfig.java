package com.zhuiyi.ms.learn.common.config.aop;


import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.zhuiyi.ms.learn.auth.AuthHandlerInterceptor;
import com.zhuiyi.ms.learn.service.DataBaseInfoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;


/**
 * @author jjluo
 * @date 2018/6/23
 */
@EnableWebMvc
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private DataBaseInfoServiceImpl dataBaseInfoService;

    @Autowired
    private Environment environment;

    @Resource
    private DataSource dataSource;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 权限控制拦截器
        String[] interceptorUrl ={"/learn-java/learn/api/v1/**", "/learn/api/v1/**", "/learn/rule/**","/learn/download/**","/learn/semantic/**",
                "/learn/task/**"};
        AuthHandlerInterceptor authHandlerInterceptor = new AuthHandlerInterceptor(dataBaseInfoService,environment,dataSource);
        registry.addInterceptor(authHandlerInterceptor).addPathPatterns(interceptorUrl).excludePathPatterns("/learn/tts");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new ResourceHttpMessageConverter());

        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        // 处理中文乱码问题
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastConverter.setSupportedMediaTypes(fastMediaTypes);
        fastConverter.setFastJsonConfig(fastJsonConfig);
        converters.add(fastConverter);
    }
}
