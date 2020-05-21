package com.zhuiyi.ms.learn.mapper;

import com.zhuiyi.ms.learn.entity.QuestionNode;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QuestionNodeMapper {
    int insert(QuestionNode record);

    int insertSelective(QuestionNode record);

    QuestionNode selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(QuestionNode record);

    int updateByPrimaryKey(QuestionNode record);

    int batchInsert(@Param("list") List<QuestionNode> list);

    void deleteByQuestionId(@Param("questionId") Long questionId);
}