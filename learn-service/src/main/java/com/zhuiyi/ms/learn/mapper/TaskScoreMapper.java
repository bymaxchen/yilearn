package com.zhuiyi.ms.learn.mapper;

import com.zhuiyi.ms.learn.entity.TaskScore;

public interface TaskScoreMapper {
    int insert(TaskScore record);

    int insertSelective(TaskScore record);

    TaskScore selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TaskScore record);

    int updateByPrimaryKey(TaskScore record);
}