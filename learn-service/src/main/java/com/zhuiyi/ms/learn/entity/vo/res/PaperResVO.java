package com.zhuiyi.ms.learn.entity.vo.res;

import lombok.Data;

import java.util.List;

@Data
public class PaperResVO {
    private int paperId;

    private String paperName;

    private int level;

    private int paperCategoryId;

    private Integer timeLimit;

    private Integer passScore;

    private Integer type;

    private String intro;

    List<PaperPartResVO> partList;
}
