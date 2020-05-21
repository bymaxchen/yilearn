package com.zhuiyi.ms.learn.controller;

import com.alibaba.fastjson.JSON;
import com.zhuiyi.ms.learn.api.TaskInfoIF;
import com.zhuiyi.ms.learn.async.TaskInfoAsync;
import com.zhuiyi.ms.learn.async.ZhongAnInfoAsync;
import com.zhuiyi.ms.learn.dto.request.RequestServiceScoreDto;
import com.zhuiyi.ms.learn.dto.request.RequestTaskLogsDto;
import com.zhuiyi.ms.learn.dto.request.RequestViolationNodesDto;
import com.zhuiyi.ms.learn.dto.response.BaseResponse;
import com.zhuiyi.ms.learn.dto.response.ResponseVO;
import com.zhuiyi.ms.learn.enums.MsgCodeEnum;
import com.zhuiyi.ms.learn.service.TaskLogInfoServiceImpl;
import com.zhuiyi.ms.learn.service.ZhongAnRemoteService;
import com.zhuiyi.ms.learn.util.ResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author rodbate
 * @Title: TaskInfoController
 * @ProjectName learn
 * @Description: TODO
 * @date 2018/9/2021:35
 */
@RestController
public class TaskInfoController implements TaskInfoIF {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskInfoController.class);

//    @Value("${taiBaoRemote}")
//    private Boolean taiBaoRemote;

//    @Value("${zhongAn}")
//    private Boolean zhongAn;

//    @Value("${voiceAuthToXunFei}")
//    private String voiceAuthToXunFei;
//
//    @Value("${voiceAuthToGuangHua}")
//    private String voiceAuthToGuangHua;
//
//    @Value("${voiceAuthIpPort}")
//    private String voiceAuthIpPort;
//
//    @Value("${voicePatternPosition}")
//    private Integer position;

//    @Value("${zhongAnUrl}")
//    private String zhongAnUrl;

    @Autowired
    private TaskLogInfoServiceImpl taskLogInfoService;

    @Autowired
    private TaskInfoAsync taskInfoAsync;

    @Autowired
    private ZhongAnRemoteService zhongAnRemoteService;

    @Autowired
    private ZhongAnInfoAsync zhongAnInfoAsync;

    @Override
    public BaseResponse saveTaskLogs(@RequestBody RequestTaskLogsDto requestTaskLogsDto){

        long startTime=System.currentTimeMillis();
        BaseResponse  baseResponse;
        try{
            baseResponse = new BaseResponse(MsgCodeEnum.SUCCESS);
            int result = 0;
            System.out.println(JSON.toJSON(requestTaskLogsDto).toString());
            result = taskLogInfoService.saveTaskLogs(requestTaskLogsDto);
            baseResponse.setData(result);
        }catch(Exception ex){
            LOGGER.info(ex.toString());
            baseResponse = new BaseResponse();
            baseResponse.setData(ex.toString());
            baseResponse.setCode(500);
        }
        long endTime=System.currentTimeMillis();
        baseResponse.setTime(endTime - startTime);
//        //是否为太保私有化项目
//        if(true == taiBaoRemote){
//            //只有通关考才进行上报讯飞和光华
//            if(requestTaskLogsDto.getTrainingType().equals(0)){
//                requestTaskLogsDto.setDbName(SessionContextHolder.getDbName());
//                requestTaskLogsDto.setVoiceAuthToGuangHua(voiceAuthToGuangHua);
//                requestTaskLogsDto.setVoiceAuthToXunFei(voiceAuthToXunFei);
//                requestTaskLogsDto.setVoiceAuthIpPort(voiceAuthIpPort);
//                requestTaskLogsDto.setPosition(position);
//                taskInfoAsyc.restXfGh(requestTaskLogsDto);
//            }
//        }
//        if(true == zhongAn){
//            try{
//                ZhongAnReqDto zhongAnReqDto = new ZhongAnReqDto();
//                zhongAnReqDto.setExamId(String.valueOf(requestTaskLogsDto.getQuestionId()));
//                zhongAnReqDto.setExamName(requestTaskLogsDto.getQuestionName());
//                zhongAnReqDto.setScore(Double.valueOf(requestTaskLogsDto.getScore()));
//                zhongAnReqDto.setSessionTime(Double.valueOf(requestTaskLogsDto.getRemainTime()));
//                zhongAnReqDto.setType(requestTaskLogsDto.getMode());
//                zhongAnReqDto.setZhongAnUrl(zhongAnUrl);
//                zhongAnReqDto.setStartDate(DateUtil.strToDate(requestTaskLogsDto.getStartTime()));
//                zhongAnReqDto.setEndDate(DateUtil.strToDate(requestTaskLogsDto.getEndTime()));
//                zhongAnReqDto.setAccessToken(requestTaskLogsDto.getUserData().getAccessToken());
//                zhongAnInfoAsyc.retryCall(zhongAnReqDto,SessionContextHolder.getDbName());
//            }catch (ParseException ex){
//                LOGGER.info(ex.toString());
//            }
//        }
        return baseResponse;
    }

    @Override
    public BaseResponse<ResponseVO> getTaskLogs(@RequestBody RequestTaskLogsDto requestTaskLogsDto) {
        BaseResponse baseResponse;
        try{
            baseResponse = new BaseResponse(MsgCodeEnum.SUCCESS);
            ResponseVO responseVO = new ResponseVO();
            responseVO = taskLogInfoService.getTaskLogs(requestTaskLogsDto);
            baseResponse.setData(responseVO);
        }catch(Exception ex){
            LOGGER.info(ex.toString());
            baseResponse = new BaseResponse();
            baseResponse.setData(ex.toString());
            baseResponse.setCode(500);
        }
        return baseResponse;
    }

    @Override
    public BaseResponse<ResponseVO> getTotalViolationNodes(@RequestBody  RequestViolationNodesDto requestViolationNodesDto) {

        BaseResponse<ResponseVO> baseResponse =  new BaseResponse(MsgCodeEnum.SUCCESS);
        ResponseVO responseVO = taskLogInfoService.getTotalViolationNodes(requestViolationNodesDto);
        baseResponse.setData(responseVO);
        return  baseResponse;
    }

    @Override
    public BaseResponse<ResponseVO> getTotalServiceScore(@RequestBody RequestServiceScoreDto requestServiceScoreDto) {

        BaseResponse<ResponseVO> baseResponse =  new BaseResponse(MsgCodeEnum.SUCCESS);
        ResponseVO responseVO = taskLogInfoService.getTotalServiceScore(requestServiceScoreDto);
        baseResponse.setData(responseVO);
        return  baseResponse;
    }

    @Override
    public BaseResponse<ResponseVO> getAllservice() {

        BaseResponse<ResponseVO> baseResponse = new BaseResponse(MsgCodeEnum.SUCCESS);
        ResponseVO responseVO = taskLogInfoService.getAllservice();
        baseResponse.setData(responseVO);
        return baseResponse;
    }

    @Override
    public BaseResponse deleteTaskLog(@RequestBody RequestTaskLogsDto requestTaskLogsDto) {

        BaseResponse baseResponse = new BaseResponse(MsgCodeEnum.SUCCESS);
        baseResponse.setData(taskLogInfoService.deleteTaskLog(requestTaskLogsDto));
        return baseResponse;
    }

    @Override
    public ResponseEntity getAllTaskLogs(@RequestBody RequestTaskLogsDto requestTaskLogsDto) {
        return ResponseBuilder.success(taskLogInfoService.getAllTaskLogs(requestTaskLogsDto));
    }

    @Override
    public BaseResponse<ResponseVO> getAllTaskLogsCount(){
        BaseResponse  baseResponse = new BaseResponse(MsgCodeEnum.SUCCESS);
        baseResponse.setData(taskLogInfoService.getAllTaskLogsCount());
        return baseResponse;
    }

}
