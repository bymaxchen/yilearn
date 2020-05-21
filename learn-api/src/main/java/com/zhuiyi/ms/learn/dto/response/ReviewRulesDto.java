package com.zhuiyi.ms.learn.dto.response;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author rodbate
 * @Title: ReviewRulesDto
 * @ProjectName learn
 * @Description: TODO
 * @date 2019/1/1616:21
 */
@Data
public class ReviewRulesDto  implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private Integer categoryId;
    private String relation;
    private Integer score;
    private String description;
    private JSONArray conditions;
    private JSONArray rawConditions;
    private Integer useType;
    private Integer ruleType;
}
