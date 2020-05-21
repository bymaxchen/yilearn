package com.zhuiyi.ms.learn.dto.response;

import com.zhuiyi.ms.learn.enums.MsgCodeEnum;
import lombok.Data;
import org.slf4j.MDC;

/**
 * 统一响应类
 *
 * @author crazyhu@wezhuiyi.com
 * @date 2018-07-17 14:35
 **/
@Data
public class BaseResponse<T> {

    private int code;

    private String msg;

    private T data;

    private long time;

    private String traceId;

    public BaseResponse() {}

    public BaseResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BaseResponse(MsgCodeEnum msgCodeEnum) {
        this.code = msgCodeEnum.getCode();
        this.msg = msgCodeEnum.getMsg();
    }


    public static BaseResponse successResponse() {
        return new BaseResponse(MsgCodeEnum.SUCCESS);
    }
}
