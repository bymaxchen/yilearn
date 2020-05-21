package com.zhuiyi.ms.learn.service.api;

import com.zhuiyi.ms.learn.entity.TQuestionRuleCategory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhuiyi.ms.learn.entity.bo.QuestionRuleCategoryTreeNodeBO;
import com.zhuiyi.ms.learn.entity.vo.req.QuestionCategoryReqVO;
import com.zhuiyi.ms.learn.entity.vo.req.QuestionRuleCategoryReqVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bymax
 * @since 2019-10-16
 */
public interface ITQuestionRuleCategoryService extends IService<TQuestionRuleCategory> {
    /**
     * 根据一个根节点，获取其所有子节点以及其本身id的list
     * @param rootId
     * @return
     */
    List<Integer> findIdListByRootId(Integer rootId);

    List<QuestionRuleCategoryTreeNodeBO> findAll();

    Boolean updateById(Integer questionRuleCategoryId, QuestionRuleCategoryReqVO questionRuleCategoryReqVO);

    Boolean deleteById(Integer questionRuleCategoryId);

    TQuestionRuleCategory create(QuestionRuleCategoryReqVO questionRuleCategoryReqVO);
}
