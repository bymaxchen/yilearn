package com.zhuiyi.ms.learn.entity.vo.req;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class QuestionRuleReqVO {
    @JSONField(name = "name")
    private String questionRuleName;

    private Integer score;

    private String description;

    @JSONField(name = "categoryId")
    private Integer questionRuleCategoryId;

    private String relation;

    private String conditions;

    private String rawConditions;

    private Integer useType;

    private Integer status;
}
