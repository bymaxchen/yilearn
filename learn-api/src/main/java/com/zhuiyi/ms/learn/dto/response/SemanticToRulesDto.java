package com.zhuiyi.ms.learn.dto.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @author rodbate
 * @Title: SemanticToRulesDto
 * @ProjectName learn
 * @Description: TODO
 * @date 2018/9/2619:12
 */
@Data
public class SemanticToRulesDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private long semanticId;
    private String semanticName;
    private int ruleId;
    private String ruleName;
    private int categoryId;
    private String categoryName;
}
