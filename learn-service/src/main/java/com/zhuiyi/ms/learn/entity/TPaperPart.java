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
 * @since 2019-10-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TPaperPart implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "paper_part_id", type = IdType.AUTO)
    private Integer paperPartId;

    /**
     * 试卷的第几部分
     */
    private Integer position;

    private Integer paperId;

    /**
     * 0不按照顺序；1按照顺序
     */
    private Boolean isInOrder;

    /**
     * 0不随机；1随机
     */
    private Boolean isRandom;

    /**
     * 分数
     */
    private Integer score;

    private String partName;

    /**
     * 0问答题；1情景题
     */
    private Integer type;

    private Integer randomCount;
}
