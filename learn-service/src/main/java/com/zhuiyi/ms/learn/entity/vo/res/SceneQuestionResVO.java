package com.zhuiyi.ms.learn.entity.vo.res;

import com.alibaba.fastjson.annotation.JSONField;
import com.zhuiyi.ms.learn.entity.TBaseQuestion;
import lombok.Data;

@Data
public class SceneQuestionResVO extends TBaseQuestion {
    private String globalRules;

    private String processRules;

    private String coreQuestion;

    private String sceneIntro;

    private String customerInfoTitleGroup;
}
