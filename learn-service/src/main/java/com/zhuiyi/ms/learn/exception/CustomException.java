package com.zhuiyi.ms.learn.exception;

import com.zhuiyi.ms.learn.enums.MsgCodeEnum;

public class CustomException extends RuntimeException {
    public CustomException(MsgCodeEnum httpCodeMsgEnum) {
        super(httpCodeMsgEnum.getMsg());
    }
}
