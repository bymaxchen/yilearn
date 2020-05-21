package com.zhuiyi.ms.learn.mapper;

import com.zhuiyi.ms.learn.entity.TQuestionCategory;
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
 * @since 2019-10-15
 */
public interface TQuestionCategoryMapper extends BaseMapper<TQuestionCategory> {
    List<TQuestionCategory> findAllWithDepth(@Param("type") Integer type);

    List<TQuestionCategory> findAllWithQuestionCount(@Param("type") Integer type);

    Integer updateForDelete(@Param("width") Integer width, @Param("rgt") Integer rgt);

    Integer updateForCreate(@Param("parentLft") Integer parentLft);

    Integer moveNode(MoveNodeInputBO moveNodeInputBO);
}
