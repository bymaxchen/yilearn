package com.zhuiyi.ms.learn.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author rodbate
 * @Title: ResponseTaskLogsDto
 * @ProjectName learn
 * @Description: TODO
 * @date 2018/9/2022:30
 */
@Data
public class ResponseTaskLogsDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private int taskId;
    private int id;
    private String sessionId;
    private Date startTimeDate;
    private Date endTimeDate;
    private String startTime;
    private String endTime;
    private int remainTime;
    private int score;
    private int taskType;
    private int endType;
    private String serviceId;
    private String serviceName;
    private int questionId;
    private String questionName;
    private List<TaskLogsDto> conversationList;
    private List<ResponseTaskScoreDto> checkRules;
    private Integer voiceAuthResult;
    private Float voiceAuthScore;
    private String voiceAuthMsg;
    private Integer mode;
    private Integer dataSize;
}
