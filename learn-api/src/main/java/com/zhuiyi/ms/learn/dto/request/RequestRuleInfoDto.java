package com.zhuiyi.ms.learn.dto.request;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author rodbate
 * @Title: RequestRuleInfoDto
 * @ProjectName learn
 * @Description: TODO
 * @date 2018/9/1815:33
 */
@Data
public class RequestRuleInfoDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private List<Integer> ruleIds;
    private String name;
    private Integer semanticId;
    private Integer categoryId;
    private String createUser;
    private Date createTime;
    private Date updateTime;
    private long createTimestamp;
    private long updateTimestamp;
    private String relation;
    private String desc;
    private String description;
    private String conditions;
    private String rawConditions;
    private int status;
    private Integer score;
    private Integer useType;
    private Integer ruleType;
    private int currentPage;
    private int pageSize;
    private Boolean pagination;
    private String dbName;

}
