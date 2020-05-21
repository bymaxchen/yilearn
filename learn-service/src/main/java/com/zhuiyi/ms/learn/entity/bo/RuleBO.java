package com.zhuiyi.ms.learn.entity.bo;

import lombok.Data;

/**
 *  nodejs端中 rule 的数据结构
 */

@Data
public class RuleBO {
    private Integer ruleId;

    private Integer nodeType;

    private Long score;
}
