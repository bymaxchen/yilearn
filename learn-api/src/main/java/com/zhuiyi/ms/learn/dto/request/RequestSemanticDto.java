package com.zhuiyi.ms.learn.dto.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author rodbate
 * @Title: RequestSemanticDto
 * @ProjectName learn
 * @Description: TODO
 * @date 2018/9/1815:38
 */
@Data
public class RequestSemanticDto implements Serializable {

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
    private int currentPage;
    private int pageSize;
    private Boolean pagination;
    private String dbName;
}
