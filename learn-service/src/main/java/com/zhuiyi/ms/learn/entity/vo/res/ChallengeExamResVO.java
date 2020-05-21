package com.zhuiyi.ms.learn.entity.vo.res;

import com.alibaba.fastjson.annotation.JSONField;
import com.zhuiyi.ms.learn.entity.TChallengeExam;
import lombok.Data;

import java.util.List;

@Data
public class ChallengeExamResVO extends TChallengeExam {
    @JSONField(name = "examList")
    private List<SceneQuestionResVO> sceneQuestionResVOList;
}
