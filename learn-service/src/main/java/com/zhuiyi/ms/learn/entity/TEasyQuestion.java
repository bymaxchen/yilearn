package com.zhuiyi.ms.learn.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
public class TEasyQuestion implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer easyQuestionId;

    /**
     * 自定义话术
     */
    private String customScript;

    /**
     * 扣分的数额
     */
    private Integer deduction;

    private Integer questionRuleId;

    /**
     * 语义标签Id
     */
    private Integer semanticTagId;

    private Integer scriptType;


}
