package com.zhuiyi.ms.learn.dto.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author rodbate
 * @Title: TaskScoreDto
 * @ProjectName learn
 * @Description: TODO
 * @date 2018/9/2019:41
 */
@Data
public class TaskScoreDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private int taskId;
    private int scoreType;
    private String categoryName;
    private String questionName;
    private int nodeId;
    private String nodeName;
    private int ruleId;
    private String ruleName;
    private int score;
    private String description;
    private String chatId;
    private Date createTime;
    private int violationStatus;
    private String dbName;
    private Integer processRetryLen;
}
