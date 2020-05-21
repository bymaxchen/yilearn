package com.zhuiyi.ms.learn.service.api;

import com.zhuiyi.ms.learn.entity.TSceneRuleCategory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhuiyi.ms.learn.entity.bo.SceneRuleCategoryTreeNodeBO;
import com.zhuiyi.ms.learn.entity.vo.req.SceneRuleCategoryReqVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bymax
 * @since 2019-10-22
 */
public interface ITSceneRuleCategoryService extends IService<TSceneRuleCategory> {
    List<Integer> findIdListByRootId(Integer rootId);

    List<SceneRuleCategoryTreeNodeBO> findAll();

    Boolean updateById(Integer questionRuleCategoryId, SceneRuleCategoryReqVO questionRuleCategoryReqVO);

    Boolean deleteById(Integer questionRuleCategoryId, Integer type);

    TSceneRuleCategory create(SceneRuleCategoryReqVO questionRuleCategoryReqVO);
}
