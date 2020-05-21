package com.zhuiyi.ms.learn.dto.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @author rodbate
 * @Title: ResponseServiceDto
 * @ProjectName learn
 * @Description: TODO
 * @date 2018/9/2117:51
 */
@Data
public class ResponseServiceDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private String serviceId;
    private String serviceName;

}
