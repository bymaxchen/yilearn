package com.zhuiyi.ms.learn.service.convertor;

import com.zhuiyi.ms.learn.common.Constants;
import com.zhuiyi.ms.learn.common.enums.DeleteStatus;
import com.zhuiyi.ms.learn.common.enums.TrainTaskStatus;
import com.zhuiyi.ms.learn.dto.request.AddTrainTaskRequest;
import com.zhuiyi.ms.learn.dto.request.QueryTaskListRequest;
import com.zhuiyi.ms.learn.dto.request.UpdateTrainTaskRequest;
import com.zhuiyi.ms.learn.dto.transfer.TrainTaskDto;
import com.zhuiyi.ms.learn.entity.TaskInfo;
import com.zhuiyi.ms.learn.entity.filter.TaskInfoFilter;
import com.zhuiyi.ms.learn.entity.vo.TaskInfoVo;
import com.zhuiyi.ms.learn.enums.MsgCodeEnum;
import com.zhuiyi.ms.learn.exception.BaseException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Date;

/**
 * 此类的描述是：
 *
 * @author crazyhu@wezhuiyi.com
 * @date 2018-09-20 14:54
 **/
public class TrainTaskConvertor {

    public static TaskInfo toTaskInfo(AddTrainTaskRequest request) {
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setQuestionId(request.getQuestionId());
        taskInfo.setTaskType(request.getTaskType());
        taskInfo.setServiceId(request.getServiceId());
        taskInfo.setServiceName(request.getServiceName());
        taskInfo.setStatus(TrainTaskStatus.UNDO.value());
        try {
            taskInfo.setStartTime(DateUtils.parseDate(request.getStartTime(), Constants.DATE_TIME_FORMAT));
        } catch (ParseException e) {
            throw new BaseException(MsgCodeEnum.DATE_PARSE_ERROR);
        }
        Date currentTime = new Date();
        taskInfo.setCreateTime(currentTime);
        taskInfo.setUpdateTime(currentTime);
        return taskInfo;
    }

    public static TaskInfo toTaskInfo(UpdateTrainTaskRequest request) {
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setId(request.getTaskId());
        taskInfo.setQuestionId(request.getQuestionId());
        taskInfo.setTaskType(request.getTaskType());
        taskInfo.setServiceId(request.getServiceId());
        taskInfo.setServiceName(request.getServiceName());
        taskInfo.setScore(request.getScore());
        taskInfo.setStatus(request.getStatus());
        try {
            if (request.getStartTime() != null) {
                taskInfo.setStartTime(DateUtils.parseDate(request.getStartTime(), Constants.DATE_TIME_FORMAT));
            }
        } catch (ParseException e) {
            throw new BaseException(MsgCodeEnum.DATE_PARSE_ERROR);
        }
        return taskInfo;
    }

    public static TrainTaskDto toTrainTaskDto(TaskInfo taskInfo) {
        TrainTaskDto dto = new TrainTaskDto();
        dto.setTaskId(taskInfo.getId());
        dto.setQuestionId(taskInfo.getQuestionId());
        dto.setTaskType(taskInfo.getTaskType());
        dto.setServiceId(taskInfo.getServiceId());
        dto.setServiceName(taskInfo.getServiceName());
        dto.setScore(taskInfo.getScore());
        dto.setStatus(taskInfo.getStatus());
        if (taskInfo.getStartTime() != null) {
            dto.setStartTime(DateFormatUtils.format(taskInfo.getStartTime(), Constants.DATE_TIME_FORMAT));
        }
        dto.setCreateTime(DateFormatUtils.format(taskInfo.getCreateTime(), Constants.DATE_TIME_FORMAT));
        dto.setUpdateTime(DateFormatUtils.format(taskInfo.getUpdateTime(), Constants.DATE_TIME_FORMAT));
        return dto;
    }

    public static TaskInfoFilter toTaskInfoFilter(QueryTaskListRequest request) {
        TaskInfoFilter filter = new TaskInfoFilter();
        if (StringUtils.isNumeric(request.getIdOrName())) {
            filter.setTaskId(Long.valueOf(request.getIdOrName()));
        } else {
            filter.setQuestionName(request.getIdOrName());
        }
        filter.setStatus(request.getStatus());
        filter.setTaskType(request.getTaskType());
        filter.setDeleteStatus(DeleteStatus.UN_DELETE.value());
        return filter;
    }

    public static TrainTaskDto toTrainTaskDto(TaskInfoVo taskInfoVo) {
        TrainTaskDto dto = new TrainTaskDto();
        dto.setTaskId(taskInfoVo.getTaskId());
        dto.setQuestionId(taskInfoVo.getQuestionId());
        dto.setQuestionName(taskInfoVo.getQuestionName());
        dto.setTaskType(taskInfoVo.getTaskType());
        dto.setServiceId(taskInfoVo.getServiceId());
        dto.setServiceName(taskInfoVo.getServiceName());
        dto.setScore(taskInfoVo.getScore());
        dto.setStatus(taskInfoVo.getStatus());
        dto.setStartTime(DateFormatUtils.format(taskInfoVo.getStartTime(), Constants.DATE_TIME_FORMAT));
        dto.setCreateTime(DateFormatUtils.format(taskInfoVo.getCreateTime(), Constants.DATE_TIME_FORMAT));
        dto.setUpdateTime(DateFormatUtils.format(taskInfoVo.getUpdateTime(), Constants.DATE_TIME_FORMAT));
        return dto;
    }
}
