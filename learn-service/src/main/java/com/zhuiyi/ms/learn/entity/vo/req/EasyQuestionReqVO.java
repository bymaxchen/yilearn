package com.zhuiyi.ms.learn.entity.vo.req;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class EasyQuestionReqVO {
    @JSONField(name = "questionTitle")
    private String questionName;

    @JSONField(name = "examQuestionTypeId")
    private Integer questionCategoryId;

    @JSONField(name = "difficultyDegree")
    private Integer level;

    private Integer questionRuleId;

    private Integer deduction;

    private Integer semanticTagId;

    private String customScript;

    private Integer scriptType;
}
