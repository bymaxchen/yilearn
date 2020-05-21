package com.zhuiyi.ms.learn.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author bymax
 * @since 2019-10-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TSceneRule implements Serializable {

    private static final long serialVersionUID = 1L;

    @JSONField(name = "id")
    @TableId(value = "scene_rule_id", type = IdType.AUTO)
    private Integer sceneRuleId;

    @JSONField(name = "name")
    private String sceneRuleName;

    /**
     * 扣分
     */
    private Integer score;

    /**
     * 评分说明
     */
    private String description;

    @TableField(exist = false)
    private Integer relatedQuestionCount;

    /**
     * 分类id
     */
    @JSONField(name = "categoryId")
    private Integer sceneRuleCategoryId;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    private String relation;

    private String conditions;

    private String rawConditions;

    private Integer useType;

    private Integer ruleType;

    private Integer status;
}
