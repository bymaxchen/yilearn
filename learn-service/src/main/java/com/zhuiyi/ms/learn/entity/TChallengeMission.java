package com.zhuiyi.ms.learn.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

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
public class TChallengeMission implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("challengeExamId")
    private Integer challengeExamId;

    @TableField("challengeExamName")
    private String challengeExamName;

    @JSONField(name = "agentIdList")
    @TableField("agentList")
    private String agentList;

    @TableField("startTime")
    private String startTime;

    private Integer status;

    @TableField( fill = FieldFill.INSERT)
    @JSONField(name = "createdAt")
    private Date createTime;

    @TableField( fill = FieldFill.INSERT)
    @JSONField(name = "updatedAt")
    private Date updateTime;



}
