package com.zhuiyi.ms.learn.common.enums;

public enum QuestionsEnum {

    EASY_QUESTION(0, "问答题"),
    SCENE_QUESTION(1, "情景题"),
    EASY_QUESTION_RULE_UNKNOWN_CATEGORY_ID(2, "问答题未分类id"),
    SCENE_QUESTION_RULE_UNKNOWN_CATEGORY_ID(4, "情景题未分类id");
    private Integer value;

    private String desc;

    QuestionsEnum(int value, String desc) {
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
