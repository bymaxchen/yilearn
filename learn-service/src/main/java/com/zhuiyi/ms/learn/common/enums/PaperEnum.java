package com.zhuiyi.ms.learn.common.enums;

public enum PaperEnum {
    FULL_MARK(100, "满分"),
    EASY_QUESTION(0, "问答题试卷"),
    SCENE_QUESTION(1, "情景题试卷"),
    COMPREHENSIVE(2, "综合题试卷");
    private Integer value;

    private String desc;

    PaperEnum(int value, String desc) {
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
