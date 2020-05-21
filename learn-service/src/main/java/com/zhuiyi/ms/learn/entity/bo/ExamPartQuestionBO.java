package com.zhuiyi.ms.learn.entity.bo;

import com.zhuiyi.ms.learn.entity.TChatLog;
import com.zhuiyi.ms.learn.entity.TExamPartQuestion;
import com.zhuiyi.ms.learn.entity.TScoreDetail;
import lombok.Data;

import java.util.List;

@Data
public class ExamPartQuestionBO extends TExamPartQuestion {
    private String questionName;

    private List<TChatLog> conversationList;

    private List<TScoreDetail>  checkRules;

    // 当考试是情景题是需要返回以下两个字段
    private String coreQuestion;

    private String sceneIntro;
}
