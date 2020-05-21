package com.zhuiyi.ms.learn.dto.request;

import lombok.Data;

/**
 * 此类的描述是：
 *
 * @author crazyhu@wezhuiyi.com
 * @date 2018-09-21 16:01
 **/
@Data
public class QueryTaskListRequest extends BaseRequest {

    private Integer status;

    private Integer taskType;

    private String idOrName;
}
