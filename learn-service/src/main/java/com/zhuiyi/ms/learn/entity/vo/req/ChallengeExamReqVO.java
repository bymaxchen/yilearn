package com.zhuiyi.ms.learn.entity.vo.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhuiyi.ms.learn.entity.TChallengeExam;
import lombok.Data;

@Data
public class ChallengeExamReqVO extends TChallengeExam {
    Page page;

    private String keyword;
}
