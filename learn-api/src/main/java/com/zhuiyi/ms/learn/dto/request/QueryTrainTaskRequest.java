package com.zhuiyi.ms.learn.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 此类的描述是：
 *
 * @author crazyhu@wezhuiyi.com
 * @date 2018-09-21 15:39
 **/
@Data
public class QueryTrainTaskRequest {

    @NotNull(message = "taskId不能为空")
    private Long taskId;
}
