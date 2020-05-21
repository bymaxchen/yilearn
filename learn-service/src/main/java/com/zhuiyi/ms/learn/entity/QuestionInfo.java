package com.zhuiyi.ms.learn.entity;

import lombok.Data;

import java.util.Date;

@Data
public class QuestionInfo {

    private Long id;

    private String questionName;

    private Long questionClassifyId;

    private Integer questionGrade;

    private Long globalRuleId;

    private Integer isForceOrder;

    private String customerName;

    private Integer customerGender;

    private String customerBankCard;

    private Long customerArrears;

    private Long customerMinRepayment;

    private Integer customerOverdue;

    private String customerDelayStage;

    private Date createTime;

    private Date updateTime;

    private Integer deleteStatus;

}