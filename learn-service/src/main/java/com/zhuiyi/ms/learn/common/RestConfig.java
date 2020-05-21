package com.zhuiyi.ms.learn.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author rodbate
 * @Title: RestConfig
 * @ProjectName learn
 * @Description: TODO
 * @date 2019/2/2519:05
 */
@Configuration
public class RestConfig {

    @Bean("OKHttp3")
    public RestTemplate OKHttp3RestTemplate(){
        OkHttp3ClientHttpRequestFactory okHttp3ClientHttpRequestFactory = new OkHttp3ClientHttpRequestFactory();
        okHttp3ClientHttpRequestFactory.setReadTimeout(30000);
        okHttp3ClientHttpRequestFactory.setConnectTimeout(30000);
        RestTemplate restTemplate = new RestTemplate(okHttp3ClientHttpRequestFactory);
        return restTemplate;
    }

}
