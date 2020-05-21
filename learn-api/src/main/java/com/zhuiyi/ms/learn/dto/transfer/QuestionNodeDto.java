package com.zhuiyi.ms.learn.dto.transfer;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 此类的描述是：
 *
 * @author crazyhu@wezhuiyi.com
 * @date 2018-09-18 19:33
 **/
@Data
public class QuestionNodeDto {

    private Long questionNodeId;

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
