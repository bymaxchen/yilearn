package com.zhuiyi.ms.learn.entity.vo.req;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class SceneRuleReqVO {
    @JSONField(name = "name")
    private String sceneRuleName;

    private Integer score;

    private String description;

    @JSONField(name = "categoryId")
    private Integer sceneRuleCategoryId;

    private String relation;

    private String conditions;

    private String rawConditions;

    private Integer useType;

    private Integer ruleType;

    private Integer status;
}
