package com.zhuiyi.ms.learn.dto.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @author rodbate
 * @Title: InvolvedRulesDto
 * @ProjectName learn
 * @Description: TODO
 * @date 2018/10/711:07
 */
@Data
public class InvolvedRulesDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private int ruleId;
    private String ruleName;
    private int ruleType;
    private int examRuleType;
}
