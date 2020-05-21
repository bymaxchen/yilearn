package com.zhuiyi.ms.learn.entity.filter;

import lombok.Data;

/**
 * 此类的描述是：
 *
 * @author crazyhu@wezhuiyi.com
 * @date 2018-09-19 16:03
 **/
@Data
public class QuestionFilter {

    private Long questionId;

    private String questionName;

    private Long questionClassifyId;

    private Integer questionGrade;

    private Integer deleteStatus;

}
