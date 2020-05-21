package com.zhuiyi.ms.learn.service.api;

import com.zhuiyi.ms.learn.entity.TExamSession;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhuiyi.ms.learn.entity.bo.ResponsePage;
import com.zhuiyi.ms.learn.entity.vo.req.ExamSessionReqVO;
import com.zhuiyi.ms.learn.entity.vo.res.ExamSessionResVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bymax
 * @since 2019-12-31
 */
public interface ITExamSessionService extends IService<TExamSession> {
    Boolean update(Integer id, ExamSessionReqVO examSessionReqVO);

    Boolean finishSession(ExamSessionReqVO examSessionReqVO);

    List<ExamSessionResVO> getChallengeMissionExam(Integer agentId);

    ResponsePage findAll(ExamSessionReqVO examSessionReqVO);
}
