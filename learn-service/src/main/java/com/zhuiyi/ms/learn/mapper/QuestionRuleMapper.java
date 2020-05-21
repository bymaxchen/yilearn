package com.zhuiyi.ms.learn.mapper;

import com.zhuiyi.ms.learn.entity.QuestionRule;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QuestionRuleMapper {
    int insert(QuestionRule record);

    int insertSelective(QuestionRule record);

    QuestionRule selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(QuestionRule record);

    int updateByPrimaryKey(QuestionRule record);

    int batchInsert(@Param("list") List<QuestionRule> list);

    void deleteByQuestionId(@Param("questionId") Long questionId);

    List<Long> selectByQuestionId(@Param("questionId") Long questionId);
}