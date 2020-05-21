package com.zhuiyi.ms.learn.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.zhuiyi.ms.learn.entity.bo.CategoryBO;
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
public class TQuestionCategory extends CategoryBO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "question_category_id", type = IdType.AUTO)
    private Integer questionCategoryId;

    private String questionCategoryName;

    private Boolean canOperate;

    private Integer type;

    /**
     *  分类下的问题数量
     */
    @TableField(exist = false)
    private Integer questionCount;

    public Integer getId() {
        return questionCategoryId;
    }
}
