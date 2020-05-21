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
 * @since 2019-10-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TBaseQuestion implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "base_question_id", type = IdType.AUTO)
    private Integer baseQuestionId;

    /**
     * 问题名称
     */
    @JSONField(name = "questionTitle")
    private String questionName;

    /**
     * 0表示问答题，1表示情景题
     */
    private Integer type;

    /**
     * 0:低；1中；2高
     */
    @JSONField(name = "difficultyDegree")
    private Integer level;

    @JSONField(name = "examQuestionTypeId")
    private Integer questionCategoryId;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


}
