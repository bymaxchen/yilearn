package com.zhuiyi.ms.learn.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author rodbate
 * @Title: ResponseCategoryDto
 * @ProjectName learn
 * @Description: TODO
 * @date 2018/9/1815:48
 */
@Data
public class ResponseCategoryDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
//    private Integer categoryId;
    private String name;
    private int categoryLevel;
    private int parentId;
    private int status;
    private long updateTimestamp;
    private long createTimestamp;
    private Date createTime;
    private Date updateTime;
    private String createUser;
    private int type;
}
