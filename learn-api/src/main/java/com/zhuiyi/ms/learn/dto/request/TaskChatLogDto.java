package com.zhuiyi.ms.learn.dto.request;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author rodbate
 * @Title: ChatLogDto
 * @ProjectName learn
 * @Description: TODO
 * @date 2018/9/2019:46
 */
@Data
public class TaskChatLogDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private int taskId;
    private String sessionId;
    private String chatId;
    private String content;
    private int identity;
    private String serviceId;
    private String serviceName;
    private Timestamp createTime;
    private String startTimestamp;
    private String endTimestamp;
    private String dbName;
    private Integer groupId;
    /**
     *  0: 未违规； 1：违规 ;默认0
     */
    private Integer isViolation;
}
