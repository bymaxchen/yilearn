package com.zhuiyi.ms.learn.exception;

import com.zhuiyi.ms.learn.enums.MsgCodeEnum;

/**
 * 业务异常类
 *
 * @author crazyhu@wezhuiyi.com
 * @createTime 2018-08-11 14:40:40
 */
public class BaseException extends RuntimeException {

    private int code;

    private String msg;

    public BaseException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public BaseException(MsgCodeEnum e) {
        super(e.getMsg());
        this.code = e.getCode();
        this.msg = e.getMsg();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}