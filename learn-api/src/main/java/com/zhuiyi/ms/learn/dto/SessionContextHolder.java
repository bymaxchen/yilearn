package com.zhuiyi.ms.learn.dto;

import com.alibaba.fastjson.JSONObject;

/**
 * TODO 暂时写一个holder来保存会话的上下文信息。
 * 实际上这一部分功能应该由aiforce提供，后续他们提供插件后再做迁移
 * 暂时保存在threadlocal中
 *
 * @author jjluo
 * @date 2018/7/9
 */
public class SessionContextHolder {

    private static ThreadLocal<JSONObject> context = ThreadLocal.withInitial(JSONObject::new);

    public static void setPid(Integer pid) {
        context.get().put("pid", pid);
    }
    public static Integer getPid() {
        return context.get().getInteger("pid");
    }

    public static void setBid(Integer bid) {
        context.get().put("bid", bid);
    }

    public static Integer getBid() {
        return context.get().getInteger("bid");
    }

    public static void setUserId(Integer userId) {
        context.get().put("userId", userId);
    }

    public static Integer getUserId() {
        return context.get().getInteger("userId");
    }

    public static void setUserName(String userName) {
        context.get().put("userName", userName);
    }

    public static String getUserName() {
        return context.get().getString("userName");
    }

    public static void setDbName(String dbName) {
        context.get().put("dbName", dbName);
    }

    public static String getDbName() {
        return context.get().getString("dbName");
    }

    public static void setCompanyId(Integer companyId) {
        context.get().put("companyId", companyId);
    }

    public static Integer getCompanyId() {
        return context.get().getInteger("companyId");
    }

    public static void setRequestId(String requestId) {
        context.get().put("requestId", requestId);
    }

    public static String getRequestId() {
        return context.get().getString("requestId");
    }
    

}
