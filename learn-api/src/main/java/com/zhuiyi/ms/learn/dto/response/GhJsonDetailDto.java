package com.zhuiyi.ms.learn.dto.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @author rodbate
 * @Title: GhJsonDetailDto
 * @ProjectName learn
 * @Description: TODO
 * @date 2019/3/814:06
 */
@Data
public class GhJsonDetailDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private String categoryName;
    private Integer categoryTotalScore;
    private Integer categoryGetScore;
}
