package com.zhuiyi.ms.learn.dto.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author rodbate
 * @Title: ViolationRulesDto
 * @ProjectName learn
 * @Description: TODO
 * @date 2018/9/2019:07
 */
@Data
public class CheckRulesDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private int scoreType;
    private int nodeId;
    private String nodeName;
    private int ruleId;
    private String ruleName;
    private int score;
    private String desc;
    private List<String> chatIds;
    private int isViolation;
    private Integer processRetryLen;
}
