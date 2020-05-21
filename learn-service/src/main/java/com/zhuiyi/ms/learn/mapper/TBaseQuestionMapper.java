package com.zhuiyi.ms.learn.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhuiyi.ms.learn.entity.TBaseQuestion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface TBaseQuestionMapper extends BaseMapper<TBaseQuestion> {
    IPage<TBaseQuestion> findAll(Page page, @Param("type") Integer type, @Param("categoryIdList") List<Integer> categoryIdList, @Param("level") Integer level, @Param("levelList") List levelList, @Param("baseQuestionId") Integer baseQuestionId, @Param("questionName") String questionName);
}
