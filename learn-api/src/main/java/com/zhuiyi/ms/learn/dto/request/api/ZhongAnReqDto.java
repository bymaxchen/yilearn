package com.zhuiyi.ms.learn.dto.request.api;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author rodbate
 * @Title: ZhongAnReqDto
 * @ProjectName learn
 * @Description: TODO
 * @date 2019/6/2020:08
 */
@Data
public class ZhongAnReqDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String accessToken;

    private String zhongAnUrl;
    /**
     *  对练开始时间
     */
    private Date startDate;
    /**
     *  对练结束时间
     */
    private Date endDate;
    /**
     *  对练模式： 1-普通练习； 2-通关考
     */
    private Integer type;
    /**
     *  得分--只有通关模式才有分数
     */
    private Double score;
    /**
     *  练习耗时
     */
    private Double sessionTime;
    /**
     *  题目Id
     */
    private String examId;
    /**
     *  题目名称
     */
    private String examName;
}
