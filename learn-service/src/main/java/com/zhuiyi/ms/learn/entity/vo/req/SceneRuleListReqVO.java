package com.zhuiyi.ms.learn.entity.vo.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.util.Collection;
import java.util.List;

@Data
public class SceneRuleListReqVO {
    private Page page;

    private String searchText;

    private Integer sceneRuleCategoryId;

    private List<Integer> ruleIdList;
}
