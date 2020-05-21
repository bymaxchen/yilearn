package com.zhuiyi.ms.learn.entity.vo.res;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class PaperQuestionResVO {
    @JSONField(name = "questionTitle")
    private String questionName;

    private Integer questionCategoryId;

    @JSONField(name = "difficultyDegree")
    private Integer level;

    private Boolean isRequired;

    private Integer score;

    private Integer semanticTagId;

    private String customScript;

    private Integer questionRuleCategoryId;

    private Integer questionRuleId;

    private String globalRules;

    private String processRules;

    private Integer scriptType;

    @JSONField(name = "id")
    private Integer baseQuestionId;

    private String coreQuestion;

    private String sceneIntro;

    private Boolean needReset;
}
