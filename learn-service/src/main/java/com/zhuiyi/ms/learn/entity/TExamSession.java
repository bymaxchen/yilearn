package com.zhuiyi.ms.learn.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.zhuiyi.ms.learn.entity.vo.res.SceneQuestionResVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author bymax
 * @since 2019-12-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TExamSession implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("agentName")
    private String agentName;

    @TableField("agentId")
    private Integer agentId;

    @TableField("taskType")
    private Integer taskType;

    @TableField("examId")
    private Integer examId;

    @TableField("examName")
    private String examName;

    @TableField("taskId")
    private Integer taskId;

    private Integer score;

    @TableField("sessionId")
    private String sessionId;

    @JSONField(name = "createdAt")
    @TableField( fill = FieldFill.INSERT)
    private Date createTime;

    @JSONField(name = "updatedAt")
    @TableField( fill = FieldFill.INSERT)
    private Date updateTime;

    @TableField("startTime")
    private String startTime;

    @TableField("audioSrc")
    private String audioSrc;

    @TableField("examQuestionType")
    private String examQuestionType;

    @TableField("examQuestionTypeId")
    private Integer examQuestionTypeId;

    @TableField("customerInfoTitleGroup")
    private String customerInfoTitleGroup;

    @TableField("endTime")
    private String endTime;

    @TableField("trainingType")
    private Integer trainingType;

    @TableField("status")
    private Integer status;

    @TableField("mode")
    private Integer mode;

    @TableField(exist = false)
    private Integer difficultyDegree;

    @TableField(exist = false)
    private SceneQuestionResVO exam;

}
