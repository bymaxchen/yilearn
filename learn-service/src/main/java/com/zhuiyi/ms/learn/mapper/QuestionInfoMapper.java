package com.zhuiyi.ms.learn.mapper;

import com.zhuiyi.ms.learn.common.Page;
import com.zhuiyi.ms.learn.entity.QuestionInfo;
import com.zhuiyi.ms.learn.entity.filter.QuestionFilter;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface QuestionInfoMapper {
    int insert(QuestionInfo record);

    int insertSelective(QuestionInfo record);

    QuestionInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(QuestionInfo record);

    int updateByPrimaryKey(QuestionInfo record);

    List<QuestionInfo> selectByFilter(@Param("filter") QuestionFilter filter, @Param("page") Page page);

    long selectCountByFilter(@Param("filter") QuestionFilter filter, @Param("page") Page page);
}