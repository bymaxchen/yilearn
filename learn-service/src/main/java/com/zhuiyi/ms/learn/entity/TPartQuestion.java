package com.zhuiyi.ms.learn.entity;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
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
public class TPartQuestion implements Serializable {

    private static final long serialVersionUID = 1L;

    @JSONField(name = "id")
    private Integer partQuestionId;

    private Integer paperPartId;

    /**
     * 0不必须；1必须
     */
    private Boolean isRequired;

    private Integer score;

    private Integer position;

    private String processRules;

    private Boolean needReset;
}
