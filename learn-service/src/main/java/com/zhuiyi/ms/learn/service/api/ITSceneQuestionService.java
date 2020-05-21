package com.zhuiyi.ms.learn.service.api;

import com.zhuiyi.ms.learn.entity.TSceneQuestion;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhuiyi.ms.learn.entity.vo.req.SceneQuestionReqVO;
import com.zhuiyi.ms.learn.entity.vo.res.SceneQuestionResVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bymax
 * @since 2019-10-16
 */
public interface ITSceneQuestionService extends IService<TSceneQuestion> {
    TSceneQuestion create(SceneQuestionReqVO sceneQuestionReqVO);

    SceneQuestionResVO findOne(Integer id);

    List<SceneQuestionResVO> findAll(List<Integer> idList);

    Boolean updateById(Integer id, SceneQuestionReqVO sceneQuestionReqVO);
}
