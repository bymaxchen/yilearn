package com.zhuiyi.ms.learn.entity.bo;

import lombok.Data;

@Data
public class ResponsePage<T> {
    private T rows;
    private int count;
}
