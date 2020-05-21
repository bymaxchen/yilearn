package com.zhuiyi.ms.learn.entity;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author rodbate
 * @Title: AudioTtsEntity
 * @ProjectName learn
 * @Description: TODO
 * @date 2019/5/1815:18
 */
@Data
public class AudioTtsEntity {

    private Integer id;
    private String text;
    private String textId;
    private String audioFilePath;
    private Timestamp startTime;

    @Override
    public String toString() {
        return "AudioTtsEntity{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", audioFile='" + audioFilePath + '\'' +
                '}';
    }
}
