package com.zhuiyi.ms.learn.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 此类的描述是：
 *
 * @author crazyhu@wezhuiyi.com
 * @date 2018-09-20 18:50
 **/
@Data
public class QueryQuestionRequest {

    @NotNull(message = "questionId不能为空")
    private Long questionId;
}
