package com.zhuiyi.ms.learn.entity.filter;

import lombok.Data;

/**
 * 此类的描述是：
 *
 * @author crazyhu@wezhuiyi.com
 * @date 2018-09-21 16:58
 **/
@Data
public class TaskInfoFilter {

    private String questionName;

    private Long taskId;

    private Integer taskType;

    private Integer status;

    private Integer deleteStatus;

}
