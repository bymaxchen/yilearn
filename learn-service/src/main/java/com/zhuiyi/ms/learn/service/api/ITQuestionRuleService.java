package com.zhuiyi.ms.learn.service.api;

import com.zhuiyi.ms.learn.entity.TEasyQuestion;
import com.zhuiyi.ms.learn.entity.TQuestionRule;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhuiyi.ms.learn.entity.bo.ResponsePage;
import com.zhuiyi.ms.learn.entity.vo.req.QuestionRuleListReqVO;
import com.zhuiyi.ms.learn.entity.vo.req.QuestionRuleReqVO;
import com.zhuiyi.ms.learn.entity.vo.res.RuleBaseQuestionResVO;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bymax
 * @since 2019-10-16
 */
public interface ITQuestionRuleService extends IService<TQuestionRule> {
    Boolean updateNullCategoryId();

    TQuestionRule create(QuestionRuleReqVO questionRuleReqVO);

    Boolean updateById(Integer id, QuestionRuleReqVO questionRuleReqVO);

    Boolean deleteById(Integer id);

    ResponsePage findAll(QuestionRuleListReqVO questionRuleListReqVO);

    Collection<TQuestionRule> findAllDetail(QuestionRuleListReqVO sceneRuleListReqVO);

    List<RuleBaseQuestionResVO> findAllQuestions(Integer ruleId);
}
