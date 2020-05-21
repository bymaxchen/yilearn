package com.zhuiyi.ms.learn.entity.vo;

import lombok.Data;

/**
 * 此类的描述是：
 *
 * @author crazyhu@wezhuiyi.com
 * @date 2018-09-18 16:00
 **/
@Data
public class QuestionClassifyVo {
    
    private Long classifyId;

    private String classifyName; 
    
    private Integer userCount;
}
