package com.zhuiyi.ms.learn.dto.request;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 此类的描述是：
 *
 * @author crazyhu@wezhuiyi.com
 * @date 2018-09-20 14:33
 **/
@Data
public class AddTrainTaskRequest {

    @NotNull(message = "questionId不能为空")
    private Long questionId;

    @NotBlank(message = "serviceId不能为空")
    private String serviceId;

    @NotNull(message = "taskType不能为空")
    private Integer taskType;

    private String serviceName;

    private String startTime;

}
