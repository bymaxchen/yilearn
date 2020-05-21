package com.zhuiyi.ms.learn.controller;

import com.zhuiyi.ms.learn.api.TrainTaskIF;
import com.zhuiyi.ms.learn.dto.request.AddTrainTaskRequest;
import com.zhuiyi.ms.learn.dto.request.DeleteTrainTaskRequest;
import com.zhuiyi.ms.learn.dto.request.QueryTaskListRequest;
import com.zhuiyi.ms.learn.dto.request.QueryTrainTaskRequest;
import com.zhuiyi.ms.learn.dto.request.UpdateTrainTaskRequest;
import com.zhuiyi.ms.learn.dto.response.BaseResponse;
import com.zhuiyi.ms.learn.dto.response.QueryTaskListResponse;
import com.zhuiyi.ms.learn.dto.transfer.TrainTaskDto;
import com.zhuiyi.ms.learn.service.TrainTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 此类的描述是：
 *
 * @author crazyhu@wezhuiyi.com
 * @date 2018-09-20 14:31
 **/
@RestController
public class TrainTaskController implements TrainTaskIF {

    @Autowired
    private TrainTaskService trainTaskService;

    @Override
    public BaseResponse<Long> addTask(@Valid @RequestBody AddTrainTaskRequest request) {
        return trainTaskService.addTask(request);
    }

    @Override
    public BaseResponse<String> updateTask(@Valid @RequestBody UpdateTrainTaskRequest request) {
        return trainTaskService.updateTask(request);
    }

    @Override
    public BaseResponse<TrainTaskDto> queryTask(@Valid @RequestBody QueryTrainTaskRequest request) {
        return trainTaskService.queryTask(request);
    }

    @Override
    public BaseResponse<QueryTaskListResponse> queryTaskList(@Valid @RequestBody QueryTaskListRequest request) {
        return trainTaskService.queryTaskList(request);
    }

    @Override
    public BaseResponse<String> deleteTask(@Valid @RequestBody DeleteTrainTaskRequest request) {
        return trainTaskService.deleteTask(request);
    }
}
