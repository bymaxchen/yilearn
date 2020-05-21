package com.zhuiyi.ms.learn.entity.bo;

import lombok.Data;

import java.util.HashMap;

@Data
public class DfsBO {
    private Integer code;

    private String message;

    private HashMap<String, String> data;
}
