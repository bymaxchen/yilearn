package com.zhuiyi.ms.learn.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class TExamPartQuestion implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *                    
     */
    @TableId(value = "exam_part_question_id", type = IdType.AUTO)
    private Integer examPartQuestionId;

    private Integer examPartId;

    private Integer baseQuestionId;

    /**
     *  0问答题，1情景题
     */
    private Integer type;

    /**
     * 音频文件的地址
     */
    private String audioSrc;

    private Integer score;

    private Integer endType;
}
