package com.zhuiyi.ms.learn.mapper;

import com.zhuiyi.ms.learn.entity.TQuestionRuleCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhuiyi.ms.learn.entity.bo.MoveNodeInputBO;
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
public interface TQuestionRuleCategoryMapper extends BaseMapper<TQuestionRuleCategory> {
    List<TQuestionRuleCategory> findAllWithDepth();

    List<TQuestionRuleCategory> findAllWithQuestionCount();

    Integer updateForDelete(@Param("width") Integer width, @Param("rgt") Integer rgt);

    Integer updateForCreate(@Param("parentLft") Integer parentLft);

    Integer moveNode(MoveNodeInputBO moveNodeInputBO);
}
