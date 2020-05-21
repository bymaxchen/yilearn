package com.zhuiyi.ms.learn.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author rodbate
 * @Title: ResponseSemanticDto
 * @ProjectName learn
 * @Description: TODO
 * @date 2018/9/1816:55
 */
@Data
public class ResponseSemanticDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
//    private Integer semanticId;
    private String name;
    private int identity;
    private int useTimes;
    private int useType;
    private String matchMode;
    private int customMatchMode;
    private String keyWords;
    private String createUser;
    private long createTimestamp;
    private Date createTime;
    private long updateTimestamp;
    private Date updateTime;
    private int status;
    private int marks;
}