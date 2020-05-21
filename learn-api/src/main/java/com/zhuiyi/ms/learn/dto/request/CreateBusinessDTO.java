package com.zhuiyi.ms.learn.dto.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author rodbate
 * @Title: CreateBusinessDTO
 * @ProjectName learn
 * @Description: TODO
 * @date 2018/11/821:16
 */
@Data
public class CreateBusinessDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String company;
    private String busId;

}
