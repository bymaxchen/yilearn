package com.zhuiyi.ms.learn.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author bymax
 * @since 2020-02-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TAudio implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "audio_id", type = IdType.AUTO)
    private Integer audioId;

    /**
     * 太保项目是session_id，在友邦项目或者标品当中，其实是exam_id
     */
    private String sessionId;

    private String mp3Url;

    private String pcmUrl;

    private String examId;

    /**
     * 10: 音频还未拼接完成 20: 音频拼接完成且可播放 40: 音频损坏
     */
    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


}
