package com.zhuiyi.ms.learn.entity.bo;

import lombok.Data;

/**
 *  保存问题规则id和对应关联的问题数量
 */

@Data
public class QuestionRuleCountBO {
    private Integer questionRuleId;

    private Integer relatedQuestionCount;
}
