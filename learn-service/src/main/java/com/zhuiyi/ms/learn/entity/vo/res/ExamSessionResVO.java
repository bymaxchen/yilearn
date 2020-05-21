package com.zhuiyi.ms.learn.entity.vo.res;

import com.zhuiyi.ms.learn.entity.TExamSession;
import lombok.Data;

@Data
public class ExamSessionResVO extends TExamSession {
    private Integer range;

    private String challengeExamName;
}
