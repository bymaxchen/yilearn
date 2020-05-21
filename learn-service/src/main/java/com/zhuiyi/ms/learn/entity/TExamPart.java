package com.zhuiyi.ms.learn.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author bymax
 * @since 2019-10-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TExamPart implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "exam_part_id", type = IdType.AUTO)
    private Integer examPartId;

    private String examId;

    /**
     * 第几部分
     */
    private Integer position;

    /**
     * 总分数
     */
    private Integer totalScore;

    /**
     * 实际得分
     */
    private Integer actualScore;


}
