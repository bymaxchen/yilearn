package com.zhuiyi.ms.learn.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author bymax
 * @since 2019-10-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TQuestionRule implements Serializable {

    private static final long serialVersionUID = 1L;

    @JSONField(name = "id")
    @TableId(value = "question_rule_id", type = IdType.AUTO)
    private Integer questionRuleId;

    @JSONField(name = "name")
    private String questionRuleName;

    private Integer score;

    private String description;

    @JSONField(name = "categoryId")
    private Integer questionRuleCategoryId;

    @TableField(exist = false)
    private Integer relatedQuestionCount;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    private String relation;

    private String conditions;

    private String rawConditions;

    private Integer useType;

    private Integer status;
}
