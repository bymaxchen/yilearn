package com.zhuiyi.ms.learn.entity.vo.req;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class SceneQuestionReqVO {
    @JSONField(name = "questionTitle")
    private String questionName;

    @JSONField(name = "examQuestionTypeId")
    private Integer questionCategoryId;

    @JSONField(name = "difficultyDegree")
    private Integer level;

    private String globalRules;

    private String processRules;

    private String coreQuestion;

    private String sceneIntro;

    private String customerInfoTitleGroup;

    private Boolean isProcessChange;
}
