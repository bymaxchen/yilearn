package com.zhuiyi.ms.learn.entity.bo;

import com.zhuiyi.ms.learn.entity.TExam;
import lombok.Data;

@Data
public class ExamBO extends TExam {
    private Integer passScore;

    private Integer timeLimit;

    private Boolean isPass;

    private Integer actualScore;

    private String paperName;

    private String identityCode;

    private String name;

    private String sessionId;
}
