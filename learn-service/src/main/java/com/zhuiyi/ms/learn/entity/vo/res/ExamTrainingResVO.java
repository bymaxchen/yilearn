package com.zhuiyi.ms.learn.entity.vo.res;

import com.alibaba.fastjson.annotation.JSONField;
import com.zhuiyi.ms.learn.entity.TExamSession;
import com.zhuiyi.ms.learn.entity.TExamTraining;
import lombok.Data;

import java.util.List;

@Data
public class ExamTrainingResVO {
    private Integer id;

    @JSONField(name = "examQuestionTypeId")
    private Integer questionCategoryId;

    @JSONField(name = "agentList")
    private List<TExamSession> sessions;

    private String examName;

    private String startTime;

    private Integer status;

    private String agentListStr;

    private Integer examId;
}
