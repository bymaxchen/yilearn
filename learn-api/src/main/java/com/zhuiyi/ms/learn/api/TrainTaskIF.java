package com.zhuiyi.ms.learn.api;

import com.zhuiyi.ms.learn.dto.request.AddTrainTaskRequest;
import com.zhuiyi.ms.learn.dto.request.DeleteTrainTaskRequest;
import com.zhuiyi.ms.learn.dto.request.QueryTaskListRequest;
import com.zhuiyi.ms.learn.dto.request.QueryTrainTaskRequest;
import com.zhuiyi.ms.learn.dto.request.UpdateTrainTaskRequest;
import com.zhuiyi.ms.learn.dto.response.BaseResponse;
import com.zhuiyi.ms.learn.dto.response.QueryTaskListResponse;
import com.zhuiyi.ms.learn.dto.transfer.TrainTaskDto;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 培训任务相关接口
 *
 * @author crazyhu@wezhuiyi.com
 * @date 2018-09-20 14:27
 **/
public interface TrainTaskIF {

    @PostMapping("/trainTask/add")
    BaseResponse<Long> addTask(AddTrainTaskRequest request);

    @PostMapping("/trainTask/update")
    BaseResponse<String> updateTask(UpdateTrainTaskRequest request);

    @PostMapping("/trainTask/detail")
    BaseResponse<TrainTaskDto> queryTask(QueryTrainTaskRequest request);

    @PostMapping("/trainTask/list")
    BaseResponse<QueryTaskListResponse> queryTaskList(QueryTaskListRequest request);

    @PostMapping("/trainTask/delete")
    BaseResponse<String> deleteTask(DeleteTrainTaskRequest request);
}
