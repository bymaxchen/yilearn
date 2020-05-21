package com.zhuiyi.ms.learn.entity;

import lombok.Data;

import java.util.Date;

@Data
public class QuestionNode {
    private Long id;

    private Long questionId;

    private Integer nodeType;

    private String nodeName;

    private Integer sequenceNo;

    private Integer scaftType;

    private Integer isInterrupt;

    private Integer interruptTime;

    private Integer isHangup;

    private Long semanticsId;

    private String customReply;

    private Long ruleId;

    private Date createTime;

    private Date updateTime;

    private Integer deleteStatus;

}