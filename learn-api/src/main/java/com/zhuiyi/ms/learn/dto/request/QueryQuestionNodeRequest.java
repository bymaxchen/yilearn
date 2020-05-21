package com.zhuiyi.ms.learn.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 此类的描述是：
 *
 * @author crazyhu@wezhuiyi.com
 * @date 2018-09-20 18:20
 **/
@Data
public class QueryQuestionNodeRequest {

    @NotNull(message = "nodeId不能为空")
    private Long nodeId;
}
