package com.zhuiyi.ms.learn.entity.vo.res;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

@Data
public class EasyQuestionResVO {
    private Integer easyQuestionId;

    private String customScript;

    private Integer deduction;

    private Integer questionRuleId;

    private Integer questionRuleCategoryId;

    private Integer semanticTagId;

    @JSONField(name = "questionTitle")
    private String questionName;

    private Integer type;

    @JSONField(name = "difficultyDegree")
    private Integer level;

    @JSONField(name = "examQuestionTypeId")
    private Integer questionCategoryId;

    private Integer scriptType;
}
