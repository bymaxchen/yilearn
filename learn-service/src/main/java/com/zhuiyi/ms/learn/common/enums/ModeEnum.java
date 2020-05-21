package com.zhuiyi.ms.learn.common.enums;

public enum ModeEnum {
    TRAIN(1, "练习"),
    EXAM(2, "考试"),
    LEARN(3, "学习");

    private Integer value;

    private String desc;

    ModeEnum(int value, String desc) {
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
