package com.zhuiyi.ms.learn.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhuiyi.ms.learn.dto.response.InvolvedRulesDto;
import com.zhuiyi.ms.learn.entity.TQuestionRule;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhuiyi.ms.learn.entity.bo.QuestionRuleCountBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author bymax
 * @since 2019-10-16
 */
public interface TQuestionRuleMapper extends BaseMapper<TQuestionRule> {
    IPage<TQuestionRule> findAll(Page page, Integer questionRuleId, String questionRuleName);

    List<QuestionRuleCountBO> findAllByQuestionRuleIdList(List<Integer> idList);

    List<InvolvedRulesDto> getInvolvedRulesBySemanticId(@Param("semanticId") long semanticId);
}
