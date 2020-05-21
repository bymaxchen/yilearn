package com.zhuiyi.ms.learn.dto.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author rodbate
 * @Title: RequestTotalViolationNodes
 * @ProjectName learn
 * @Description: TODO
 * @date 2018/9/2113:28
 */
@Data
public class RequestViolationNodesDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private String startTime;
    private String endTime;
    private List<String> serviceIds;
    private int scoreType;
    private int violationStatus;
}
