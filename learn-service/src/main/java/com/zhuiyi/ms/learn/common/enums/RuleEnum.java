package com.zhuiyi.ms.learn.common.enums;

public enum RuleEnum {

    QUESTION_RULE_TYPE(0, "问答规则"),
    SCENE_RULE_TYPE(2, "情景规则");
    private Integer value;

    private String desc;

    RuleEnum(int value, String desc) {
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
