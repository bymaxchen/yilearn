package com.zhuiyi.ms.learn.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.security.SecureRandom;

/**
 * @author rodbate
 * @Title: TaskInfoDto
 * @ProjectName learn
 * @Description: TODO
 * @date 2018/9/2118:44
 */
@Data
public class TotalTaskInfoDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private String serviceName;
    private String serviceId;
    private int taskId;
}

