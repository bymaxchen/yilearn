package com.zhuiyi.ms.learn.service.api;

import com.zhuiyi.ms.learn.entity.TExamTraining;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhuiyi.ms.learn.entity.bo.ResponsePage;
import com.zhuiyi.ms.learn.entity.vo.req.ExamTrainingReqVO;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bymax
 * @since 2020-01-14
 */
public interface ITExamTrainingService extends IService<TExamTraining> {
    TExamTraining create(ExamTrainingReqVO examTrainingReqVO);

    Boolean delete(Integer id);

    Boolean update(Integer id, ExamTrainingReqVO examTrainingReqVO);

    ResponsePage findAll(ExamTrainingReqVO examTrainingReqVO);
}
