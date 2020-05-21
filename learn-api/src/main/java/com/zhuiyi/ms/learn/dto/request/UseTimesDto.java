package com.zhuiyi.ms.learn.dto.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author rodbate
 * @Title: UseTimesDto
 * @ProjectName learn
 * @Description: TODO
 * @date 2018/9/1815:39
 */
@Data
public class UseTimesDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private int useTimes;
    private int semanticId;
}
