package com.zhuiyi.ms.learn.entity.bo;

import lombok.Data;

@Data
public class SceneQuestionBO {
    private Integer id;

    private String questionTitle;

    private Integer examQuestionTypeId;

    private Integer difficultyDegree;
}
