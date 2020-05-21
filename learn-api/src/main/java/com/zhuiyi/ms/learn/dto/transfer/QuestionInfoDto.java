package com.zhuiyi.ms.learn.dto.transfer;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 此类的描述是：
 *
 * @author crazyhu@wezhuiyi.com
 * @date 2018-09-19 17:40
 **/
@Data
public class QuestionInfoDto {

    private Long questionId;

    private String questionName;

    private Long questionClassifyId;

    private Integer questionGrade;

    private Integer isForceOrder;

    private String customerName;

    private Integer customerGender;

    private String customerBankCard;

    private Long customerArrears;

    private Long customerMinRepayment;

    private Integer customerOverdue;

    private String customerDelayStage;

    private String createTime;

    private String updateTime;

    private List<Long> globalRuleIdList;
}
