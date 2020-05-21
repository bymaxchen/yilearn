package com.zhuiyi.ms.learn.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhuiyi.ms.learn.entity.TExamTraining;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhuiyi.ms.learn.entity.vo.req.ExamTrainingReqVO;
import com.zhuiyi.ms.learn.entity.vo.res.ExamTrainingResVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author bymax
 * @since 2020-01-14
 */
public interface TExamTrainingMapper extends BaseMapper<TExamTraining> {
    IPage<ExamTrainingResVO> findAll(Page page, @Param("keyword") String keyword, @Param("agent") Integer agent,
                                     @Param("status") Integer status, @Param("level") Integer level, @Param("questionCategoryId") Integer questionCategoryId);
}
