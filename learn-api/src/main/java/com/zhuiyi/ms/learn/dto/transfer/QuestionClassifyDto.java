package com.zhuiyi.ms.learn.dto.transfer;

import lombok.Data;

/**
 * 此类的描述是：
 *
 * @author crazyhu@wezhuiyi.com
 * @date 2018-09-18 15:43
 **/
@Data
public class QuestionClassifyDto {

    private Long classifyId;

    private String classifyName;

    private Integer usedCount;
}
