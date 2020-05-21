package com.zhuiyi.ms.learn.entity.vo.req;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhuiyi.ms.learn.entity.TExamSession;
import com.zhuiyi.ms.learn.entity.TExamTraining;
import lombok.Data;

import java.util.List;

@Data
public class ExamTrainingReqVO extends TExamTraining {
    private Page page;

    @JSONField(name = "agentList")
    private List<TExamSession> examSessionList;

    private Integer agent;

    private String keyword;

    @JSONField(name = "difficultyDegree")
    private Integer level;

    @JSONField(name = "examQuestionTypeId")
    private Integer questionCategoryId;

    private Integer mode;
}
