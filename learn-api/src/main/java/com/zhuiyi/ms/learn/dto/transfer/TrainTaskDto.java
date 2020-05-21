package com.zhuiyi.ms.learn.dto.transfer;

import lombok.Data;

import java.util.Date;

/**
 * 此类的描述是：
 *
 * @author crazyhu@wezhuiyi.com
 * @date 2018-09-21 15:40
 **/
@Data
public class TrainTaskDto {

    private Long taskId;

    private Long questionId;

    private String questionName;

    private Integer taskType;

    private String serviceId;

    private String serviceName;

    private Integer score;

    private Integer status;

    private String startTime;

    private String createTime;

    private String updateTime;
}
