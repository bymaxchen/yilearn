package com.zhuiyi.ms.learn.dto.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author rodbate
 * @Title: RequestFactorDto
 * @ProjectName learn
 * @Description: TODO
 * @date 2018/9/1815:37
 */
@Data
public class FactorDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private int checkType;
    private String checkValue;
}
