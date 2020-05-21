package com.zhuiyi.ms.learn.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

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
 * @since 2019-10-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TPaperCategory extends CategoryBO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "paper_category_id", type = IdType.AUTO)
    private Integer paperCategoryId;

    private String paperCategoryName;

    @TableField(exist = false)
    private Integer paperCount;

    private Boolean canOperate;

    public Integer getId() {
        return paperCategoryId;
    }
}
