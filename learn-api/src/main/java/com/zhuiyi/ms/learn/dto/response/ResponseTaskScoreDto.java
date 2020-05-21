package com.zhuiyi.ms.learn.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author rodbate
 * @Title: TaskScoreDto
 * @ProjectName learn
 * @Description: TODO
 * @date 2018/9/2022:39
 */
@Data
public class ResponseTaskScoreDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private int taskId;
    private int scoreType;
    private int ruleId;
    private String ruleName;
    private int score;
    @JsonProperty(value = "desc")
    private String description;
    private String chatId;
    private int isViolation;
    private int nodeId;
    private String nodeName;
    private Integer processRetryLen;
}
