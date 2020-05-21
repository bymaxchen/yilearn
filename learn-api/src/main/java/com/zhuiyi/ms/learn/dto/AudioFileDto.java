package com.zhuiyi.ms.learn.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author rodbate
 * @Title: AudioFileDto
 * @ProjectName demo
 * @Description: TODO
 * @date 2019/5/2015:23
 */
@Data
public class AudioFileDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String text;
    private byte[] audio;
}
