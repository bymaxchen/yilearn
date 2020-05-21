package com.zhuiyi.ms.learn.dto.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author rodbate
 * @Title: RequestCategoryDto
 * @ProjectName learn
 * @Description: TODO
 * @date 2018/9/1815:34
 */
@Data
public class RequestCategoryDto implements Serializable {

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
    private int currentPage;
    private int pageSize;
    private int offset;
    private int limit;
    private Boolean pagination;
    private String dbName;
}
