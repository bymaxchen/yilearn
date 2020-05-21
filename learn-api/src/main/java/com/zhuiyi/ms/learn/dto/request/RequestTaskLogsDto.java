package com.zhuiyi.ms.learn.dto.request;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author rodbate
 * @Title: RequestSessionDto
 * @ProjectName learn
 * @Description: TODO
 * @date 2018/9/2019:21
 */
@Data
public class RequestTaskLogsDto implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     *  用于太保定制化项目： 0 是通关考  1是培训
     */
    private Integer trainingType;

    /**
     * 音频文件地址
     */
    private String audioSrc;
    private String examId;
    private int id;
    private int questionId;
    /**
     * 题目类型--太保场景类型
     */
    private Integer questionTypeId;
    private String questionType;
    private String userId;
    /**
     *  题目名称--太保场景名称
     */
    private String questionName;
    private Integer taskId;
    private String sessionId;
    private String startTime;
    private String endTime;
    private int score;
    private Integer taskType;
    private int remainTime;
    private String serviceId;
    private String serviceName;
    private JSONArray checkRules;
    private JSONArray conversations;
    private int status;
    private int deleteStatus;
    private int endType;
    private String searchType;
    private String dbName;
    /**
     *  用户接收光华所需字段
     */
    private UserTaiBaoDto userData;
    private String voiceAuthToGuangHua;
    private String voiceAuthToXunFei;
    private String voiceAuthIpPort;
    private Integer mode;
    private Integer position;
    private Integer currentPage;
    private Integer pageSize;

    @Override
    public String toString() {
        return "RequestTaskLogsDto{" +
                "trainingType=" + trainingType +
                ", audioSrc='" + audioSrc + '\'' +
                ", examId='" + examId + '\'' +
                ", id=" + id +
                ", questionId=" + questionId +
                ", questionTypeId=" + questionTypeId +
                ", questionType='" + questionType + '\'' +
                ", userId='" + userId + '\'' +
                ", questionName='" + questionName + '\'' +
                ", taskId=" + taskId +
                ", sessionId='" + sessionId + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", score=" + score +
                ", taskType=" + taskType +
                ", remainTime=" + remainTime +
                ", serviceId='" + serviceId + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", checkRules=" + checkRules +
                ", conversations=" + conversations +
                ", status=" + status +
                ", deleteStatus=" + deleteStatus +
                ", endType=" + endType +
                ", searchType='" + searchType + '\'' +
                ", dbName='" + dbName + '\'' +
                ", userData=" + userData +
                ", voiceAuthToGuangHua='" + voiceAuthToGuangHua + '\'' +
                ", voiceAuthToXunFei='" + voiceAuthToXunFei + '\'' +
                ", voiceAuthIpPort='" + voiceAuthIpPort + '\'' +
                ", mode=" + mode +
                ", position=" + position +
                ", currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                '}';
    }
}
