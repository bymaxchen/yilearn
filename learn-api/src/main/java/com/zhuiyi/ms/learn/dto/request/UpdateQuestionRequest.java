package com.zhuiyi.ms.learn.dto.request;

import com.zhuiyi.ms.learn.dto.transfer.QuestionNodeDto;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 此类的描述是：
 *
 * @author crazyhu@wezhuiyi.com
 * @date 2018-09-19 14:11
 **/
@Data
public class UpdateQuestionRequest {

    @NotNull(message = "questionId不能为空")
    private Long questionId;

    private String questionName;

    private Long questionClassifyId;

    private Integer questionGrade;

    private List<Long> globalRuleIdList;

    private Integer isForceOrder;

    private String customerName;

    private Integer customerGender;

    private String customerBankCard;

    private Long customerArrears;

    private Long customerMinRepayment;

    private Integer customerOverdue;

    private String customerDelayStage;

    private List<QuestionNodeDto> nodeList;
}
