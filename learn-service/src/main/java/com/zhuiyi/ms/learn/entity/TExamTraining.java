package com.zhuiyi.ms.learn.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @since 2020-01-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TExamTraining implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("examName")
    private String examName;

    @TableField("examId")
    private Integer examId;

    @TableField("startTime")
    private String startTime;

    private Integer status;

    @TableField("agentList")
    private String agentList;

    @JSONField(name = "createdAt")
    @TableField( fill = FieldFill.INSERT)
    private Date createTime;

    @JSONField(name = "updatedAt")
    @TableField( fill = FieldFill.INSERT)
    private Date updateTime;

    @TableField("createUserName")
    private String createUserName;

    @TableField("createUserId")
    private Integer createUserId;

    @TableField("trainingType")
    private Integer trainingType;


}
