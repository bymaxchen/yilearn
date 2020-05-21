package com.zhuiyi.ms.learn.enums;

/**
 * 统一异常码
 * 错误码统一由9位的负整数组成: 如下
 *   组 模块 保留位 具体位
 * - 16  10    00    000
 * @see <a href="https://confluence.in.wezhuiyi.com/pages/viewpage.action?pageId=9896370">详细文档</a>
 * @author crazyhu@wezhuiyi.com
 * @date 2018-07-17 14:43
 **/
public enum MsgCodeEnum {

    /**
     * 处理成功
     */
    SUCCESS(0, "success"),

    DATE_PARSE_ERROR(-161100111, "日期转换错误"),

    /**
     * 参数错误
     */
    PARAM_INVALID(-161100444, "PARAM_INVALID"),


    /**
     *  未传入BusId
     */
    BUSINESS_ID_NOT_FOUND(-10001, "BUSINESS_ID_NOT_FOUND"),

    BUSINESS_DB_NOT_FOUND(-10005,"BUSINESS_DB_NOT_FOUND"),

    UNKONWN_ERROR(-110000111,"未知异常"),


    DUPLICATE_ENTRY(-161100400, "DUPLICATE_ENTRY"),

    /**
     * 处理失败
     */
    SYSTEM_ERROR(-161100999, "SYSTEM_ERROR");

    private int code;

    private String msg;

    MsgCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static MsgCodeEnum getEnum(String msg) {
        for(MsgCodeEnum httpCodeMsgEnum:MsgCodeEnum.values()) {
            if (httpCodeMsgEnum.getMsg().equals(msg)) {
                return httpCodeMsgEnum;
            }
        }
        return null;
    }
}
