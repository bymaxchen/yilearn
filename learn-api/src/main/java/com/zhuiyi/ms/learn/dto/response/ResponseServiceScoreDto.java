package com.zhuiyi.ms.learn.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author rodbate
 * @Title: RequestTotalScoreDto
 * @ProjectName learn
 * @Description: TODO
 * @date 2018/9/2114:34
 */
@Data
public class ResponseServiceScoreDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private int taskId;
    private String serviceId;
    private String serviceName;
    private int trainTimes;
    private int processViolationTimes;
    private int globalViolationTimes;
    private float averageScore;

}
