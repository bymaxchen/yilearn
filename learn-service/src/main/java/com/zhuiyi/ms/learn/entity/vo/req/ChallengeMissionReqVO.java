package com.zhuiyi.ms.learn.entity.vo.req;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhuiyi.ms.learn.entity.TChallengeMission;
import com.zhuiyi.ms.learn.entity.TExamSession;
import lombok.Data;

import java.util.List;

@Data
public class ChallengeMissionReqVO extends TChallengeMission {
    private String keyword;

    private Page page;

    @JSONField(name = "agentList")
    private List<TExamSession> examSessionList;
}
