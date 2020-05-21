package com.zhuiyi.ms.learn.entity;

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
public class TPaper implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "paper_id", type = IdType.AUTO)
    private Integer paperId;

    private String paperName;

    /**
     * 试卷难度；0高、1中、2低
     */
    private Integer level;

    /**
     * 试卷说明
     */
    private String intro;

    private Integer paperCategoryId;

    @TableField(exist = false)
    private String paperCategoryName;

    private Integer timeLimit;

    private Integer passScore;

    private Boolean needReset;

    /**
     * 0只有问答题；1只有情景题；2两者都有
     */
    private Integer type;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
