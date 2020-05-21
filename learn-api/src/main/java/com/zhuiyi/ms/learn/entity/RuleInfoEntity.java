package com.zhuiyi.ms.learn.entity;

import lombok.Data;

/**
 * @author rodbate
 * @Title: RuleInfoEntity
 * @ProjectName learn
 * @Description: TODO
 * @date 2019/1/1616:36
 */
@Data
public class RuleInfoEntity {

    private Integer id;
    private String name;
    private Integer categoryId;
    private String relation;
    private Integer score;
    private String description;
    private String conditions;
    private String rawConditions;
    private Integer useType;
    private Integer ruleType;
    private String createUser;
    private String createTime;
    private String updateTime;

}
