package com.zhuiyi.ms.learn.entity.vo.req;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class QuestionRuleCategoryReqVO {
    private Integer questionRuleCategoryId;

    private Integer parentId;

    private String questionRuleCategoryName;
}
