package com.zhuiyi.ms.learn.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author rodbate
 * @Title: ResponseTotalNodeDto
 * @ProjectName learn
 * @Description: TODO
 * @date 2018/9/2113:08
 */
@Data
public class ResponseTotalNodeDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private Date createTime;
    private String dateSign;
    private int nodeId;
    private String nodeName;
    private int ruleId;
    private String ruleName;
    private int violationNums;

}
