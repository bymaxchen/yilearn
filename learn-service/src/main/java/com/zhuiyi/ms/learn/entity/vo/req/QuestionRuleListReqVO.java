package com.zhuiyi.ms.learn.entity.vo.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.util.List;

@Data
public class QuestionRuleListReqVO {
    private Page page;

    private String searchText;

    private Integer questionRuleCategoryId;

    private List<Integer> ruleIdList;
}
