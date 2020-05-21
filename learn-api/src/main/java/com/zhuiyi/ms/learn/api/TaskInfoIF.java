package com.zhuiyi.ms.learn.api;

import com.zhuiyi.ms.learn.dto.request.RequestServiceScoreDto;
import com.zhuiyi.ms.learn.dto.request.RequestTaskLogsDto;
import com.zhuiyi.ms.learn.dto.request.RequestViolationNodesDto;
import com.zhuiyi.ms.learn.dto.response.BaseResponse;
import com.zhuiyi.ms.learn.dto.response.ResponseVO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author rodbate
 * @Title: TaskInfoIF
 * @ProjectName learn
 * @Description: TODO
 * @date 2018/9/2021:36
 */
@RequestMapping("/learn/task")
public interface TaskInfoIF {

    @PostMapping("/saveTaskLogs")
    BaseResponse  saveTaskLogs(RequestTaskLogsDto requestTaskLogsDto);

    @PostMapping("/getTaskLogs")
    BaseResponse<ResponseVO> getTaskLogs(RequestTaskLogsDto requestTaskLogsDto);

    @PostMapping("/getTotalViolationNodes")
    BaseResponse<ResponseVO> getTotalViolationNodes(RequestViolationNodesDto requestViolationNodesDto);

    @PostMapping("/getTotalServiceScore")
    BaseResponse<ResponseVO> getTotalServiceScore(RequestServiceScoreDto requestServiceScoreDto);

    @PostMapping("/getAllservice")
    BaseResponse<ResponseVO> getAllservice();

    @PostMapping("/deleteTaskLog")
    BaseResponse deleteTaskLog(RequestTaskLogsDto requestTaskLogsDto);

    @PostMapping("/getAllTaskLogs")
    ResponseEntity getAllTaskLogs(RequestTaskLogsDto requestTaskLogsDto);

    @GetMapping("/getAllTaskLogsCount")
    BaseResponse<ResponseVO> getAllTaskLogsCount();

}
