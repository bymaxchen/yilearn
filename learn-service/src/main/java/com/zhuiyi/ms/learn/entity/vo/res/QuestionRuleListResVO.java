package com.zhuiyi.ms.learn.entity.vo.res;

import lombok.Data;

@Data
public class QuestionRuleListResVO {
    private Integer questionRuleId;

    private String questionRuleName;

    private Integer score;

    private String description;

    private Integer questionRuleCategoryId;

    private Integer relatedQuestionCount;

}
