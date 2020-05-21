package com.zhuiyi.ms.learn.entity;

import lombok.Data;

import java.util.Date;

@Data
public class TaskInfo {
    
    private Long id;

    private Long questionId;

    private Integer taskType;

    private String serviceId;

    private String serviceName;

    private Integer score;

    private Integer status;

    private Date startTime;

    private Date createTime;

    private Date updateTime;

    private Integer deleteStatus;

}