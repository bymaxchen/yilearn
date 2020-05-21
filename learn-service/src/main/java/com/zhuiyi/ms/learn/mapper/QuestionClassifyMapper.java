package com.zhuiyi.ms.learn.mapper;

import com.zhuiyi.ms.learn.common.Page;
import com.zhuiyi.ms.learn.entity.QuestionClassify;
import com.zhuiyi.ms.learn.entity.vo.QuestionClassifyVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionClassifyMapper {
    int insert(QuestionClassify record);

    int insertSelective(QuestionClassify record);

    QuestionClassify selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(QuestionClassify record);

    int updateByPrimaryKey(QuestionClassify record);

    List<QuestionClassifyVo> queryList(@Param("page") Page page);

    int deleteByPrimaryKey(@Param("classifyId") Long classifyId);
}