package com.zhuiyi.ms.learn.dto.response;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author rodbate
 * @Title: ResponseRuleInfoDto
 * @ProjectName learn
 * @Description: TODO
 * @date 2018/9/1815:43
 */
@Data
public class ResponseRuleInfoDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
//    private Integer ruleId;
    private String name;
    private int semanticId;
    private int categoryId;
    private String createUser;
    private Date createTimeDate;
    private Date updateTimeDate;
    private String createTime;
    private String updateTime;
    private long createTimestamp;
    private long updateTimestamp;
    private String relation;
    private String description;
    private String desc;
    private String conditions;
    private String rawConditions;
    private int status;
    private int score;
    private int useType;
    private int ruleType;
    private JSONArray conditionsJson;
    private JSONArray rawConditionsJson;
}
