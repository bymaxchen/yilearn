package com.zhuiyi.ms.learn.exception;

import com.alibaba.fastjson.JSONException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.zhuiyi.ms.learn.enums.MsgCodeEnum;
import com.zhuiyi.ms.learn.util.LoggerUtil;
import com.zhuiyi.ms.learn.util.ResponseBuilder;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ValidationException;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        LoggerUtil.error(ex.getMessage(), ex);
        return ResponseBuilder.error(MsgCodeEnum.SYSTEM_ERROR.getCode(), ex.toString(), HttpStatus.OK);
    }

    @ExceptionHandler({MissingServletRequestParameterException.class, MethodArgumentNotValidException.class, ValidationException.class, JSONException.class, HttpMessageNotReadableException.class})
    public final ResponseEntity<Object> handleMissingServletRequestParameterException(Exception ex, WebRequest request) {
        LoggerUtil.error(ex.getMessage(), ex);
        return ResponseBuilder.error(MsgCodeEnum.PARAM_INVALID.getCode(), MsgCodeEnum.PARAM_INVALID.getMsg() + ":" + ex.toString(), HttpStatus.OK);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public final ResponseEntity<Object> handleDupException(DuplicateKeyException ex) {
        return ResponseBuilder.error(MsgCodeEnum.DUPLICATE_ENTRY.getCode(), MsgCodeEnum.DUPLICATE_ENTRY.getMsg(), HttpStatus.OK);
    }


    @ExceptionHandler(CustomException.class)
    public final ResponseEntity<Object> handleCustomException(CustomException ex) {
        MsgCodeEnum httpCodeMsgEnum = MsgCodeEnum.getEnum(ex.getMessage());
        return ResponseBuilder.error(httpCodeMsgEnum, HttpStatus.OK);
    }



}
