package com.zhuiyi.ms.learn.common.enums;

public enum SceneRuleEnum {
    PROCESS_RULE(0, "流程规则"),
    GLOBAL_RULE(1, "全局规则"),
    NODE_GLOBAL_RULE(1, "nodejs全局规则类型"),
    NODE_PROCESS_RULE(2, "nodejs流程规则类型"),
    NODE_DEFAULT_RULE(3, "nodejs默认规则类型"),
    CLIENT_NODE_TYPE(1, "流程节点当中的客户节点"),
    SERVICE_NODE_TYPE(2, "流程节点当中的坐席节点"),
    PROCESS_RULE_UNKNOWN_CATEGORY_ID(3, "流程规则未分类id"),
    GLOBAL_RULE_UNKNOWN_CATEGORY_ID(6, "全局规则未分类id");

    private Integer value;

    private String desc;

    SceneRuleEnum(int value, String desc) {
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
