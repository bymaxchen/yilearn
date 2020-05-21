package com.zhuiyi.ms.learn.interceptor;

import com.zhuiyi.ms.learn.util.LoggerUtil;
import okhttp3.Interceptor;
import okhttp3.Request;
import okio.Buffer;

import java.io.IOException;

public class LogInterceptor implements Interceptor {

    public static String TAG = "LogInterceptor";

    private static String bodyToString(final Request request){

        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        long startTime = System.currentTimeMillis();
        okhttp3.Response response = chain.proceed(chain.request());
        long endTime = System.currentTimeMillis();
        long duration=endTime-startTime;
        okhttp3.MediaType mediaType = response.body().contentType();

        LoggerUtil.info("----------Start----------------");
        LoggerUtil.info("| "+request.toString());
        String method=request.method();

        // 上传文件到tame-dfs不打印请求体（无意义的字符串太多）
        if("POST".equals(method) && !request.url().toString().contains("tame/dfs")){
            LoggerUtil.info("request:\n" + bodyToString(request));
        }


        String content = response.body().string();
        LoggerUtil.info("| Response:" + content);
        LoggerUtil.info("----------End:"+duration+"毫秒----------");
        return response.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();
    }
}