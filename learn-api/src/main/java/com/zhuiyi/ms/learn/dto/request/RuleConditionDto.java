package com.zhuiyi.ms.learn.dto.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author rodbate
 * @Title: RuleConditionDto
 * @ProjectName learn
 * @Description: TODO
 * @date 2018/9/1815:40
 */
@Data
public class RuleConditionDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private String relation;
    private int messageType;
    private int checkScope;
    private List<FactorDto> factors;
}
