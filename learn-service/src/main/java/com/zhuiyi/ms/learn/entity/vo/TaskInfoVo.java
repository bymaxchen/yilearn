package com.zhuiyi.ms.learn.entity.vo;

import lombok.Data;

import java.util.Date;

/**
 * 此类的描述是：
 *
 * @author crazyhu@wezhuiyi.com
 * @date 2018-09-21 17:12
 **/
@Data
public class TaskInfoVo {

    private Long taskId;

    private Long questionId;

    private String questionName;

    private Integer status;

    private Integer taskType;

    private Date startTime;

    private Integer score;

    private String serviceId;

    private String serviceName;

    private Date createTime;

    private Date updateTime;
}
