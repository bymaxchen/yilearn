package com.zhuiyi.ms.learn.dto.request;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author rodbate
 * @Title: TaskInfoDto
 * @ProjectName learn
 * @Description: TODO
 * @date 2018/9/2019:37
 */
@Data
public class TaskInfoDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private int questionId;
    private int taskId;
    private int taskType;
    private int endType;
    private String sessionId;
    private String serviceId;
    private String serviceName;
    private int score;
    private int status;
    private Timestamp startTime;
    private Timestamp endTime;
    private Timestamp updateTime;
    private int deleteStatus;
    private int remainTime;
    private String questionName;
    private String dbName;
    private Integer mode;

}
