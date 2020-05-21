package com.zhuiyi.ms.learn.mapper;

import com.zhuiyi.ms.learn.entity.TSceneRuleCategory;
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
 * @since 2019-10-22
 */
public interface TSceneRuleCategoryMapper extends BaseMapper<TSceneRuleCategory> {
    List<TSceneRuleCategory> findAllWithDepth();

    List<TSceneRuleCategory> findAllWithSceneCount();

    Integer updateForDelete(@Param("width") Integer width, @Param("rgt") Integer rgt);

    Integer updateForCreate(@Param("parentLft") Integer parentLft);

    Integer moveNode(MoveNodeInputBO moveNodeInputBO);
}
