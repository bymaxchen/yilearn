package com.zhuiyi.ms.learn.service.api;

import com.zhuiyi.ms.learn.entity.TBaseQuestion;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhuiyi.ms.learn.entity.bo.ResponsePage;
import com.zhuiyi.ms.learn.entity.vo.req.QuestionListReqVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bymax
 * @since 2019-10-15
 */
public interface ITBaseQuestionService extends IService<TBaseQuestion> {
    ResponsePage findAll(QuestionListReqVO questionListReqVO);

    Boolean updateNullCategoryId(Integer type);
}
