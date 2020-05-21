package com.zhuiyi.ms.learn.entity.vo.res;

import com.alibaba.fastjson.annotation.JSONField;
import com.zhuiyi.ms.learn.entity.TChallengeMission;
import com.zhuiyi.ms.learn.entity.TExamSession;
import lombok.Data;

import java.util.List;

@Data
public class TChallengeMissionResVO  extends TChallengeMission {

    @JSONField(name = "agentList")
    private List<TExamSession> examSessionList;
}
