package com.zhuiyi.ms.learn.mapper;

import com.zhuiyi.ms.learn.common.Page;
import com.zhuiyi.ms.learn.entity.TaskInfo;
import com.zhuiyi.ms.learn.entity.filter.TaskInfoFilter;
import com.zhuiyi.ms.learn.entity.vo.TaskInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskInfoMapper {
    int insert(TaskInfo record);

    int insertSelective(TaskInfo record);

    TaskInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TaskInfo record);

    int updateByPrimaryKey(TaskInfo record);

    List<TaskInfoVo> selectByFilter(@Param("filter") TaskInfoFilter filter, @Param("page") Page page);

    Long selectCountByFilter(@Param("filter") TaskInfoFilter filter, @Param("page") Page page);
}