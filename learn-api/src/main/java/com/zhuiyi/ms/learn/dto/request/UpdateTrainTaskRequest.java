package com.zhuiyi.ms.learn.dto.request;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 此类的描述是：
 *
 * @author crazyhu@wezhuiyi.com
 * @date 2018-09-21 14:31
 **/
@Data
public class UpdateTrainTaskRequest {

    @NotNull(message = "taskId不能为空")
    private Long taskId;

    @NotNull(message = "questionId不能为空")
    private Long questionId;

    private Integer taskType;

    private String serviceId;

    private String serviceName;

    private String startTime;

    private String endTime;

    private Integer score;

    private Integer status;

}
