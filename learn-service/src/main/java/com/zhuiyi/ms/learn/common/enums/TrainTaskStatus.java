package com.zhuiyi.ms.learn.common.enums;

/**
 * 培训任务状态枚举类
 *
 * @author crazyhu@wezhuiyi.com
 * @date 2018-09-20 14:47
 **/
public enum TrainTaskStatus {

    /**
     * 培训任务状态枚举类
     */
    UNKNOWN(0, "未知类型"),
    UNDO(1, "未进行"),
    DOING(2, "进行中"),
    DONE(3, "已完成");

    private Integer value;

    private String desc;

    TrainTaskStatus(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer value() {
        return value;
    }

    public String desc() {
        return desc;
    }

    public static TrainTaskStatus findByValue(int value) {
        for (TrainTaskStatus e : TrainTaskStatus.values()) {
            if (e.value.equals(value)) {
                return e;
            }
        }
        return UNKNOWN;
    }
}
