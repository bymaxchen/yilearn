
package com.zhuiyi.ms.learn.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhuiyi.ms.learn.common.RestConfig;
import com.zhuiyi.ms.learn.entity.bo.DfsBO;
import com.zhuiyi.ms.learn.interceptor.LogInterceptor;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * 此类的描述是：构造Http返回信息
 *
 * @author GorsonLi@wezhuiyi.com
 * @create 2018-04-20 19:37
 **/
@Component
public class HttpUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtil.class);

    private static final int HTTP_TIMEOUT_SECOND = 120;

    private static final int HTTP_CONNECT_TIMEOUT_SECOND = 120;


    @Value("${dfs.url}")
    private String dfsUrl;



    /**
     * 上传字节数组到dfs
     * 返回dfs的资源地址
     * @param fileName
     * @param body
     * @return
     */
    public String uploadBytesToDfs(String fileName, byte[] body) {
        String url = dfsUrl + "/tame/dfs/uploadByByte?supportType=local&serveName=learn&fileName=" + fileName;
        JSONObject res = (JSONObject) httpPost(url, body);

        DfsBO dfsBO = res.toJavaObject(DfsBO.class);
        if (dfsBO.getCode().equals(0)) {
            return dfsBO.getData().get("fileUrl");
        }
        return  "";
    }

    public static byte[] downloadBytesFromDfs(String url) {
        AnnotationConfigApplicationContext applicationContext
                = new AnnotationConfigApplicationContext(RestConfig.class);
        RestTemplate client = (RestTemplate) applicationContext.getBean("OKHttp3");

        HttpEntity<String> requestEntity = new HttpEntity<String>("");

        ResponseEntity<byte[]> result = client.exchange(url, HttpMethod.POST, requestEntity, byte[].class);

        byte[] body = result.getBody();

        return body;
    }

    public static Object httpGet(String url, Headers headers) {
        if (StringUtils.isEmpty(url)) {
            LOGGER.warn("Http get failed, url is null: " + url);
            return Collections.emptyList();
        }
        Request request = new Request.Builder().headers(headers).url(url).build();
        return getResponse(getHttpClient("", ""), request, true);
    }

    public static Object httpGet(String url) {
        return httpGet(url, true);
    }

    public static Object httpGet(String url, Boolean packaged) {
        return httpGet("", "", url, packaged);
    }

    /**
     * Http get 请求
     *
     * @param proxyHost 代理的主机
     * @param proxyPort 代理的端口
     * @param url       访问的Url
     * @return JSON对象列表
     */
    public static Object httpGet(String proxyHost, String proxyPort, String url, Boolean packaged) {
        if (StringUtils.isEmpty(url)) {
            LOGGER.warn("Http get failed, url is null: " + url);
            return Collections.emptyList();
        }

        Request request = new Request.Builder().url(url).build();
        return getResponse(getHttpClient(proxyHost, proxyPort), request, packaged);
    }

    public static Object httpPost(String url, Object body) {
        return httpPost("", "", url, body, true, null);
    }

    public static Object httpPost(String url, Object body, Headers headers) {
        return httpPost("", "", url, body, true, headers);
    }

    // 发送byte数组的请求
    public static Object httpPost(String url, byte[] body) {
        return httpPostBytes("", "", url, body, true);
    }

    public static Object httpPost(String url, Object body, Boolean packaged) {
        return httpPost("", "", url, body, packaged, null);
    }

    /**
     * Http post 请求
     *
     * @param proxyHost 代理的主机
     * @param proxyPort 代理的端口
     * @param url       访问的Url
     * @param body      实体内容
     * @return JSON对象列表
     */
    public static Object httpPost(String proxyHost, String proxyPort, String url, Object body, Boolean packaged, Headers headers) {
        if (StringUtils.isEmpty(url) || body == null) {
            LOGGER.warn("Http post failed, url or body is null: " + url + " " + body);
            return Collections.emptyList();
        }
        return getResponse(getHttpClient(proxyHost, proxyPort), generateRequest(url, HttpMethod.POST.name(), body, headers), packaged);
    }

    /**
     * post bytes数组
     * @param proxyHost
     * @param proxyPort
     * @param url
     * @param body
     * @param packaged
     * @return
     */
    public static Object httpPostBytes(String proxyHost, String proxyPort, String url, byte[] body, Boolean packaged) {
        if (StringUtils.isEmpty(url) || body == null) {
            LOGGER.warn("Http post failed, url or body is null: " + url + " " + body);
            return Collections.emptyList();
        }
        return getResponse(getHttpClient(proxyHost, proxyPort), generateBytesRequest(url, HttpMethod.POST.name(), body), packaged);
    }

    public static Object httpPut(String url, Object body) {
        return httpPut("", "", url, body);
    }

    public static Object httpPut(String proxyHost, String proxyPort, String url, Object body) {
        if (StringUtils.isEmpty(url) || body == null) {
            LOGGER.warn("Http put failed, url or body is null: " + url + " " + body);
            return Collections.emptyList();
        }
        return getResponse(getHttpClient(proxyHost, proxyPort), generateRequest(url, HttpMethod.PUT.name(), body));
    }

    /**
     * @param url       访问的Url
     * @param body      实体内容
     * @return JSON对象列表
     */
    private static Request generateRequest(String url, String method, Object body) {
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"),
                JSON.toJSONString(body));
        return new Request.Builder().url(url).method(method.toUpperCase(), requestBody).build();
    }

    private static Request generateBytesRequest(String url, String method, byte[] body) {
        RequestBody requestBody = FormBody.create(null, body);
        return new Request.Builder().url(url).method(method.toUpperCase(), requestBody).build();
    }

    /**
     * @param url       访问的Url
     * @param body      实体内容
     * @return JSON对象列表
     */
    private static Request generateRequest(String url, String method, Object body, Headers headers) {
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"),
                JSON.toJSONString(body));
        return new Request.Builder().headers(headers).url(url).method(method.toUpperCase(), requestBody).build();
    }

    /**
     * 构造客户端，分为使用代理和没有代理
     *
     * @param proxyHost 代理的主机
     * @param proxyPort 代理的端口
     * @return 客户端
     */
    private static OkHttpClient getHttpClient(String proxyHost, String proxyPort) {
        OkHttpClient client;

        if (!StringUtils.isAnyEmpty(proxyHost, proxyPort)) {
            LOGGER.info("User http proxy: " + proxyHost + " " + proxyPort);
            client = new OkHttpClient().newBuilder().addInterceptor(new LogInterceptor())
                    .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, Integer.parseInt(proxyPort))))
                    .readTimeout(HTTP_TIMEOUT_SECOND, TimeUnit.SECONDS).connectTimeout(HTTP_CONNECT_TIMEOUT_SECOND, TimeUnit.SECONDS)
                    .build();
        } else {
            client = new OkHttpClient().newBuilder().addInterceptor(new LogInterceptor())
                    .readTimeout(HTTP_TIMEOUT_SECOND, TimeUnit.SECONDS).connectTimeout(HTTP_CONNECT_TIMEOUT_SECOND, TimeUnit.SECONDS)
                    .build();
        }

        return client;
    }


    public static Object getResponse(OkHttpClient client, Request request) {
        return getResponse(client, request, true);
    }

    /**
     * 构造返回结果
     *
     * @param client  客户端
     * @param request 请求
     * @return
     */
    private static Object getResponse(OkHttpClient client, Request request, Boolean packaged) {
        Response response;

        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            LoggerUtil.error("Http request exception: " + request.url().toString() + "\n" + JSON.toJSONString(e));
            return Collections.emptyList();
        }

        if (!response.isSuccessful()) {
            LoggerUtil.error("Http request failed: " + request.url().toString() + "\n" + JSON.toJSONString(response));
            return Collections.emptyList();
        }

        try {
            if (response.body() == null) {
                return new Object();
            }

            String resStr = response.body().string();

            return JSON.parse(resStr);

        } catch (IOException e) {
            LoggerUtil.error("Http request failed, Cannot get body string: " + request.url().toString() + " " + JSON.toJSONString(response));
            return Collections.emptyList();
        }
    }

}