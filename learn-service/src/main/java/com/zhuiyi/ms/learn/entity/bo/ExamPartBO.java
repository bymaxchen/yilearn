package com.zhuiyi.ms.learn.entity.bo;

import com.zhuiyi.ms.learn.entity.TExamPart;
import lombok.Data;

import java.util.List;

@Data
public class ExamPartBO extends TExamPart {
    private List<ExamPartQuestionBO> questionList;

    private String partName;

    private Integer type;
}
