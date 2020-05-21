package com.zhuiyi.ms.learn.service.api;

import com.zhuiyi.ms.learn.entity.TChallengeExam;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhuiyi.ms.learn.entity.bo.ResponsePage;
import com.zhuiyi.ms.learn.entity.vo.req.ChallengeExamReqVO;
import com.zhuiyi.ms.learn.entity.vo.res.ChallengeExamResVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bymax
 * @since 2019-12-30
 */
public interface ITChallengeExamService extends IService<TChallengeExam> {
    ResponsePage findAll(ChallengeExamReqVO challengeExamReqVO);

    TChallengeExam create(ChallengeExamReqVO challengeExamReqVO);

    ChallengeExamResVO findOne(Integer id);
}
