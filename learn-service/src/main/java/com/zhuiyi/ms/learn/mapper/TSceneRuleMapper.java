package com.zhuiyi.ms.learn.mapper;

import com.zhuiyi.ms.learn.dto.response.InvolvedRulesDto;
import com.zhuiyi.ms.learn.entity.TSceneRule;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author bymax
 * @since 2019-10-22
 */
public interface TSceneRuleMapper extends BaseMapper<TSceneRule> {
    List<InvolvedRulesDto> getInvolvedRulesBySemanticId(@Param("semanticId") long semanticId);
}
