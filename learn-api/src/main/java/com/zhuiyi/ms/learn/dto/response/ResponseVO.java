package com.zhuiyi.ms.learn.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * @author rodbate
 * @Title: ResponseVO
 * @ProjectName learn
 * @Description: TODO
 * @date 2018/9/1911:12
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer currentPage;
    private Integer pageSize;
    private Integer total;
    private Integer allPages;
    private Object data;
    private String message;
}
