package com.zhuiyi.ms.learn.entity;

import lombok.Data;

import java.util.Date;

@Data
public class QuestionRule {

    private Long id;

    private Long questionId;

    private Long globalRuleId;

    private Date createTime;

}