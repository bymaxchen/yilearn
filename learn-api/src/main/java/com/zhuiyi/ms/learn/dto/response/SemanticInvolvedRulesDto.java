package com.zhuiyi.ms.learn.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author rodbate
 * @Title: SemanticInvolvedRulesDto
 * @ProjectName learn
 * @Description: TODO
 * @date 2018/10/711:04
 */
@Data
public class SemanticInvolvedRulesDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private long semanticId;
    private List<InvolvedRulesDto> involvedRules;
    private int involvedRuleNums;
}
