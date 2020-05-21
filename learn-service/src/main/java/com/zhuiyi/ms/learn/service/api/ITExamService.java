package com.zhuiyi.ms.learn.service.api;

import com.zhuiyi.ms.learn.entity.TExam;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhuiyi.ms.learn.entity.bo.ExamBO;
import com.zhuiyi.ms.learn.entity.vo.req.ExamResultReqVO;
import com.zhuiyi.ms.learn.entity.vo.res.ExamResultResVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bymax
 * @since 2019-10-24
 */
public interface ITExamService extends IService<TExam> {
    ExamBO create(ExamResultReqVO examResultReqVO);

    ExamResultResVO findOne(String examId);
}
