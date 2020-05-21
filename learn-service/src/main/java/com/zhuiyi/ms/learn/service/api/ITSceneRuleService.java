package com.zhuiyi.ms.learn.service.api;

import com.zhuiyi.ms.learn.entity.TQuestionRule;
import com.zhuiyi.ms.learn.entity.TSceneRule;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhuiyi.ms.learn.entity.bo.ResponsePage;
import com.zhuiyi.ms.learn.entity.vo.req.SceneRuleListReqVO;
import com.zhuiyi.ms.learn.entity.vo.req.SceneRuleReqVO;
import com.zhuiyi.ms.learn.entity.vo.res.RuleBaseQuestionResVO;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bymax
 * @since 2019-10-22
 */
public interface ITSceneRuleService extends IService<TSceneRule> {
    Boolean updateNullCategoryId(Integer type);

    TSceneRule create(SceneRuleReqVO sceneRuleListReqVO);

    Boolean updateById(Integer id, SceneRuleReqVO questionRuleReqVO);

    Boolean deleteById(Integer id);

    ResponsePage findAll(SceneRuleListReqVO sceneRuleListReqVO);

    Collection<TSceneRule> findAllDetail(SceneRuleListReqVO sceneRuleListReqVO);

    List<RuleBaseQuestionResVO> findAllQuestions(Integer ruleId);
}
