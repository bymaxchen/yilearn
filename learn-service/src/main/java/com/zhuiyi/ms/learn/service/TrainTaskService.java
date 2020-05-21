package com.zhuiyi.ms.learn.service;
import com.zhuiyi.ms.learn.common.Constants;
import com.zhuiyi.ms.learn.common.Page;
import com.zhuiyi.ms.learn.common.enums.DeleteStatus;
import com.zhuiyi.ms.learn.dto.request.AddTrainTaskRequest;
import com.zhuiyi.ms.learn.dto.request.DeleteTrainTaskRequest;
import com.zhuiyi.ms.learn.dto.request.QueryTaskListRequest;
import com.zhuiyi.ms.learn.dto.request.QueryTrainTaskRequest;
import com.zhuiyi.ms.learn.dto.request.UpdateTrainTaskRequest;
import com.zhuiyi.ms.learn.dto.response.BaseResponse;
import com.zhuiyi.ms.learn.dto.response.QueryTaskListResponse;
import com.zhuiyi.ms.learn.dto.transfer.TrainTaskDto;
import com.zhuiyi.ms.learn.entity.QuestionInfo;
import com.zhuiyi.ms.learn.entity.TaskInfo;
import com.zhuiyi.ms.learn.entity.filter.TaskInfoFilter;
import com.zhuiyi.ms.learn.entity.vo.TaskInfoVo;
import com.zhuiyi.ms.learn.mapper.QuestionInfoMapper;
import com.zhuiyi.ms.learn.mapper.TaskInfoMapper;
import com.zhuiyi.ms.learn.service.convertor.TrainTaskConvertor;
import com.zhuiyi.ms.learn.enums.MsgCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 此类的描述是：
 *
 * @author crazyhu@wezhuiyi.com
 * @date 2018-09-20 14:43
 **/
@Slf4j
@Service
public class TrainTaskService {

    @Autowired
    private TaskInfoMapper taskInfoMapper;

    @Autowired
    private QuestionInfoMapper questionInfoMapper;

    public BaseResponse<Long> addTask(AddTrainTaskRequest request) {
        TaskInfo taskInfo = TrainTaskConvertor.toTaskInfo(request);
        taskInfoMapper.insertSelective(taskInfo);

        BaseResponse<Long> response = new BaseResponse<>(MsgCodeEnum.SUCCESS);
        response.setData(taskInfo.getId());
        return response;
    }

    public BaseResponse<String> updateTask(UpdateTrainTaskRequest request) {
        TaskInfo taskInfo = TrainTaskConvertor.toTaskInfo(request);
        taskInfoMapper.updateByPrimaryKeySelective(taskInfo);

        BaseResponse<String> response = new BaseResponse<>(MsgCodeEnum.SUCCESS);
        return response;
    }

    public BaseResponse<TrainTaskDto> queryTask(QueryTrainTaskRequest request) {
        TaskInfo taskInfo = taskInfoMapper.selectByPrimaryKey(request.getTaskId());
        TrainTaskDto data = null;
        if (taskInfo != null) {
            data = TrainTaskConvertor.toTrainTaskDto(taskInfo);
            QuestionInfo questionInfo = questionInfoMapper.selectByPrimaryKey(taskInfo.getQuestionId());
            if (questionInfo != null) {
                data.setQuestionName(questionInfo.getQuestionName());
            }
        }

        BaseResponse<TrainTaskDto> response = new BaseResponse<>(MsgCodeEnum.SUCCESS);
        response.setData(data);
        return response;
    }

    public BaseResponse<String> deleteTask(DeleteTrainTaskRequest request) {
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setId(request.getTaskId());
        taskInfo.setDeleteStatus(DeleteStatus.DELETED.value());
        taskInfo.setUpdateTime(new Date());
        taskInfoMapper.updateByPrimaryKeySelective(taskInfo);

        BaseResponse<String> response = new BaseResponse<>(MsgCodeEnum.SUCCESS);
        return response;
    }

    public BaseResponse<QueryTaskListResponse> queryTaskList(QueryTaskListRequest request) {
        // 设置查询条件
        TaskInfoFilter filter = TrainTaskConvertor.toTaskInfoFilter(request);
        // 设置分页参数
        Page page = Page.toPage(request.getPageNo(), request.getPageSize());

        List<TaskInfoVo> voList = taskInfoMapper.selectByFilter(filter, page);
        Long count = taskInfoMapper.selectCountByFilter(filter, page);

        // VO转化为DTO
        List<TrainTaskDto> dataList = Optional.ofNullable(voList).orElse(Collections.emptyList())
                .stream().map(TrainTaskConvertor::toTrainTaskDto)
                .collect(Collectors.toList());

        BaseResponse<QueryTaskListResponse> response = new BaseResponse<>(MsgCodeEnum.SUCCESS);
        QueryTaskListResponse data = new QueryTaskListResponse();
        data.setCount(count);
        data.setDataList(dataList);
        response.setData(data);
        return response;
    }
}
