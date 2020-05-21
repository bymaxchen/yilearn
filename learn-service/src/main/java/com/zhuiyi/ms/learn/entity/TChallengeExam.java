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
 * @since 2019-12-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TChallengeExam implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("challengeExamName")
    private String challengeExamName;

    @JSONField(name = "examListStr")
    @TableField("examList")
    private String examList;

    @JSONField(name = "createdAt")
    @TableField( fill = FieldFill.INSERT)
    private Date createTime;

    @JSONField(name = "updatedAt")
    @TableField( fill = FieldFill.INSERT)
    private Date updateTime;


}
