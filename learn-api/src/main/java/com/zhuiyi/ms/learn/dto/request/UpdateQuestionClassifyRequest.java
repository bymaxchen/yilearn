package com.zhuiyi.ms.learn.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 此类的描述是：
 *
 * @author crazyhu@wezhuiyi.com
 * @date 2018-09-18 15:25
 **/
@Data
public class UpdateQuestionClassifyRequest {

    @NotNull(message = "classifyId不能为空")
    private Long classifyId;

    private String classifyName;
}
