package com.zhuiyi.ms.learn.entity.vo.res;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class RuleBaseQuestionResVO {
    @JSONField(name = "id")
    private Integer baseQuestionId;

    @JSONField(name = "questionTitle")
    private String questionName;
}
