package com.zhuiyi.ms.learn.dto.request;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.io.Serializable;

/**
 * @author rodbate
 * @Title: TaskRemoteDto
 * @ProjectName learn
 * @Description: TODO
 * @date 2019/2/2814:21
 */
@Data
public class TaskXFRemoteDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *  唯一标识，和声纹注册使用同一字段
     *  初阶段使用: frontUserId；需和光华进一步确认
     */
    private String identifierId;

    /**
     *  音频文件URL路径
     */
    private String audioUrl;

    private String sourceUrl;

    /**
     *  自由说: 1
     */
    private String type;

    /**
     *  不传，讯飞接口保留字段
     */
    private Integer number;

    private String voiceAuthToXunFei;

    private Integer position;



}
