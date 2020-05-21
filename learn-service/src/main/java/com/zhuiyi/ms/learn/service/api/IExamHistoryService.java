package com.zhuiyi.ms.learn.service.api;

import org.apache.poi.ss.formula.functions.T;

/**
 * @author rodbate
 * @Title: IExamHistoryService
 * @ProjectName learn
 * @Description: TODO
 * @date 2019/12/309:54
 */
public interface IExamHistoryService {

    Object saveExamHistory(Object object);

    /**
     *  友邦推送
     * @param object
     */
    void ybPushResult(Object object);

    /**
     *  推送智能教育
     */
    void pushEducation(Object object);

    /**
     *  众安推送
     */

    /**
     *  声纹认证--是否需要返回结果，作为下一步浩宜方-或其他第三方推送结果
     */

    /**
     *  浩宜推送
     */
}
