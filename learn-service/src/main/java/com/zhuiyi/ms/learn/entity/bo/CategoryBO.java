package com.zhuiyi.ms.learn.entity.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class CategoryBO {
    private Integer lft;

    private Integer rgt;

    @TableField(exist = false)
    private Integer depth;

    @TableField(exist = false)
    private Integer id;
}
