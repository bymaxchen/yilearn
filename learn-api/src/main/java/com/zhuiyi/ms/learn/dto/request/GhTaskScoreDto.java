package com.zhuiyi.ms.learn.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author rodbate
 * @Title: GhCheckRulesDto
 * @ProjectName learn
 * @Description: TODO
 * @date 2019/3/616:21
 */
@Data
public class GhTaskScoreDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private int scoreType;

    private int score;

    private int isViolation;
    private String categoryName;
}
