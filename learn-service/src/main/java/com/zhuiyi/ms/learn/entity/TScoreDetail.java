package com.zhuiyi.ms.learn.entity;

import com.alibaba.fastjson.annotation.JSONField;
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
 * @since 2019-10-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TScoreDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "score_detail_id", type = IdType.AUTO)
    private Integer scoreDetailId;

    private Integer ruleId;

    private Integer isViolation;

    private Integer processRetryLen;

    private Integer examPartQuestionId;

    /**
     * 考试中，每道题目对应规则得分详情所在的位置。从0开始
     */
    private Integer position;

    private String ruleName;

    @JSONField(name = "desc")
    private String description;

    private Integer score;

    private Integer scoreType;

    private Long nodeId;

    private String nodeName;

    @JSONField(name = "chatIds")
    private String chatId;
}
