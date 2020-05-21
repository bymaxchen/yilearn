package com.zhuiyi.ms.learn.dto.request;

import lombok.Data;

/**
 * 此类的描述是：
 *
 * @author crazyhu@wezhuiyi.com
 * @date 2018-09-19 15:17
 **/
@Data
public class QueryQuestionListRequest extends BaseRequest {

    private Long questionClassifyId;

    private Integer questionGrade;

    private String idOrName;
}
