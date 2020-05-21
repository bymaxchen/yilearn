package com.zhuiyi.ms.learn.common.enums;

public enum AudioEnum{
    START(0, "开始音频拼接,第一个片段"),
    MID(1, "音频拼接中间过程"),
    END(2, "音频拼接结束，最后一个片段"),
    NOT_FINISHED(10, "音频还未拼接完成"),
    SUCCESS(20, "音频拼接完成且可播放"),
    NOT_FOUND(30, "音频未找到"),
    BROKEN(40, "音频损坏"),
    MP3_TYPE(100, "MP3格式"),
    PCM_TYPE(200, "PCM格式"),;
    private Integer value;

    private String desc;

    AudioEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer value() {
        return value;
    }

    public String desc() {
        return desc;
    }
}
