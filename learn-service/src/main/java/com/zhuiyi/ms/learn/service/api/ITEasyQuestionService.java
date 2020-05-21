package com.zhuiyi.ms.learn.service.api;

import com.zhuiyi.ms.learn.entity.TEasyQuestion;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhuiyi.ms.learn.entity.vo.req.EasyQuestionReqVO;
import com.zhuiyi.ms.learn.entity.vo.res.EasyQuestionResVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bymax
 * @since 2019-10-15
 */
public interface ITEasyQuestionService extends IService<TEasyQuestion> {
    TEasyQuestion create(EasyQuestionReqVO easyQuestionReqVO);

    EasyQuestionResVO findOne(Integer id);

    Boolean updateById(Integer id, EasyQuestionReqVO easyQuestionReqVO);
}
