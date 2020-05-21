package com.zhuiyi.ms.learn.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 此类的描述是：
 *
 * @author crazyhu@wezhuiyi.com
 * @date 2018-09-20 18:10
 **/
@Data
public class UpdateQuestionNodeRequest {

    @NotNull(message = "nodeId不能为空")
    private Long nodeId;

    @NotNull(message = "questionId不能为空")
    private Long questionId;

    private Integer nodeType;

    private String nodeName;

    private Integer sequenceNo;

    private Integer scaftType;

    private Integer isInterrupt;

    private Integer interruptTime;

    private Integer isHangup;

    private Long semanticsId;

    private Long ruleId;

    private List<String> customReplyList;
}
