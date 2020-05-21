package com.zhuiyi.ms.learn.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author rodbate
 * @Title: TtsReqDTO
 * @ProjectName learn
 * @Description: TODO
 * @date 2019/5/2911:22
 */
@Data
public class TtsReqDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *  text经前端md5后的16位小写字符串
     */
    @NotNull(message = "textId不可为null")
    private String textId;

    /**
     *  文本内容：保留字段，暂不接收
     */
    private String text;
}
