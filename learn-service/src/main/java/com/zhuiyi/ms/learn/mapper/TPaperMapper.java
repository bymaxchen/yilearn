package com.zhuiyi.ms.learn.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhuiyi.ms.learn.entity.TPaper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface TPaperMapper extends BaseMapper<TPaper> {
    IPage<TPaper> findAll(Page page, @Param("type") Integer type, @Param("categoryIdList") List<Integer> categoryIdList, @Param("level") Integer level, @Param("paperId") Integer paperId, @Param("paperName") String paperName);

    List<TPaper> findAllByQuestionId(@Param("questionId") Integer questionId);

    List<TPaper> findAllByPartIdList(List<Integer> paperPartIdList);
}
