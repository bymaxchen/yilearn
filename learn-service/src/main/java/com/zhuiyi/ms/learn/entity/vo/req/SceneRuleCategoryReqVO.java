package com.zhuiyi.ms.learn.entity.vo.req;

import lombok.Data;

@Data
public class SceneRuleCategoryReqVO {
    private Integer sceneRuleCategoryId;

    private Integer parentId;

    private String sceneRuleCategoryName;
}
