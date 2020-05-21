package com.zhuiyi.ms.learn.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author rodbate
 * @Title: TaskLogsDto
 * @ProjectName learn
 * @Description: TODO
 * @date 2018/9/2022:38
 */
@Data
public class TaskLogsDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private int taskId;
    private String sessionId;
    private String content;
    private int identity;
    private String chatId;
    private Date createTimeDate;
    private String createTime;
    private String serviceId;
    private String serviceName;
    private String startTimestamp;
    private String endTimestamp;
    private Integer groupId;
    /**
     *  0: 未违规； 1：违规 ;默认0
     */
    private Integer isViolation;

}
