package com.zhuiyi.ms.learn.dto.request;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 添加问题分类请求类
 *
 * @author crazyhu@wezhuiyi.com
 * @date 2018-09-18 14:17
 **/
@Data
public class AddQuestionClassifyRequest {

    @NotBlank(message = "分类名称不能为空")
    private String classifyName;
}
