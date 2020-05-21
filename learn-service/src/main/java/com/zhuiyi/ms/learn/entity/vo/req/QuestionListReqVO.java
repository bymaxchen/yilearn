package com.zhuiyi.ms.learn.entity.vo.req;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.util.List;

@Data
public class QuestionListReqVO {
    private Page page;

    private Integer type;

    @JSONField(name = "difficultyDegree")
    private Integer level;

    @JSONField(name = "difficultyDegreeList")
    private List<Integer> levelList;

    @JSONField(name = "examQuestionTypeId")
    private Integer questionCategoryId;

    private String searchText;
}
