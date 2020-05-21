package com.zhuiyi.ms.learn.entity.vo.req;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class QuestionCategoryReqVO {
    private Integer questionCategoryId;

    private Integer parentId;

    @JSONField(name = "name")
    private String questionCategoryName;

    private Integer type;
}
