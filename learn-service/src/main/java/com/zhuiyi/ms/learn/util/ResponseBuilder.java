package com.zhuiyi.ms.learn.util;


import com.zhuiyi.ms.learn.dto.response.BaseResponse;
import com.zhuiyi.ms.learn.enums.MsgCodeEnum;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public class ResponseBuilder {
    public static <T> ResponseEntity build(T data, MsgCodeEnum codeMsg, HttpStatus httpStatus){
        BaseResponse<T> response = new BaseResponse<>();
        response.setCode(codeMsg.getCode());
        response.setMsg(codeMsg.getMsg());
        response.setData(data);
        response.setTraceId(MDC.get("trace_uuid"));
        return new ResponseEntity(response, httpStatus);
    }

    public static <T> ResponseEntity build(T data, int code, String msg, HttpStatus httpStatus){
        BaseResponse<T> response = new BaseResponse<>();
        response.setCode(code);
        response.setMsg(msg);
        response.setData(data);
        response.setTraceId(MDC.get("trace_uuid"));
        return new ResponseEntity(response, httpStatus);
    }

    public static <T> ResponseEntity success(T data){
        return build(data, MsgCodeEnum.SUCCESS, HttpStatus.OK);
    }


    public static <T> ResponseEntity error(MsgCodeEnum codeMsg, HttpStatus httpStatus){
        return build(null, codeMsg, httpStatus);
    }

    public static <T> ResponseEntity error(MsgCodeEnum codeMsg){
        return build(null, codeMsg, HttpStatus.OK);
    }

    public static <T> ResponseEntity error(int code, String msg, HttpStatus httpStatus) {
        return build(null,code, msg, httpStatus);
    }
}
