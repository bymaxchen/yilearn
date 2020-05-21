package com.zhuiyi.ms.learn.service;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhuiyi.ms.learn.dto.request.GuangHuaRecordDto;
import com.zhuiyi.ms.learn.dto.request.TaskRemoteGhDto;
import com.zhuiyi.ms.learn.mapper.GuanghuaRecordMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.sql.Timestamp;

/**
 * @author rodbate
 * @Title: GuanghuaRemoteService
 * @ProjectName learn
 * @Description: TODO
 * @date 2019/3/417:46
 */
@Service
public class GuanghuaRemoteService{


    private static final Logger LOGGER = LoggerFactory.getLogger(GuanghuaRemoteService.class);

    private static final Integer RETRY_TIMES = 3;

    @Autowired
    private GuanghuaRecordMapper guanghuaRecordMapper;

    public String remoteCall(TaskRemoteGhDto taskRemoteGhDto) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(taskRemoteGhDto.getVoiceAuthToGuangHua())
                .queryParam("coopCode",taskRemoteGhDto.getCoopCode())
                .queryParam("frontUserId",taskRemoteGhDto.getFrontUserId())
                .queryParam("accessToken",taskRemoteGhDto.getAccessToken())
                .queryParam("examId",taskRemoteGhDto.getExamId())
                .queryParam("getScore",taskRemoteGhDto.getGetScore())
                .queryParam("taskId",taskRemoteGhDto.getTaskId())
                .queryParam("jsonDetail",JSONObject.toJSONString(taskRemoteGhDto.getJsonDetail()))
                .queryParam("voiceAuthResult",JSONObject.toJSONString(taskRemoteGhDto.getVoiceAuthResponse()));
        HttpEntity entity = new HttpEntity(httpHeaders);
        long begin = System.currentTimeMillis();
        HttpEntity<String> response = restTemplate.exchange(builder.build().encode().toUri(),HttpMethod.POST,
                entity,String.class);
        long end = System.currentTimeMillis();
        LOGGER.info("保存通过成绩考接口响应时间: =" + (end - begin) + "ms");
        LOGGER.info("保存通关考成绩入参>>" + builder.toUriString());
        return response.toString();
    }


    public void retryCall(TaskRemoteGhDto taskRemoteGhDto,String dbName) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(taskRemoteGhDto.getVoiceAuthToGuangHua())
                .queryParam("coopCode",taskRemoteGhDto.getCoopCode())
                .queryParam("frontUserId",taskRemoteGhDto.getFrontUserId())
                .queryParam("sceneId", taskRemoteGhDto.getSceneId())
                .queryParam("sceneTypeId", taskRemoteGhDto.getSceneTypeId())
                .queryParam("sceneName", taskRemoteGhDto.getSceneName())
                .queryParam("sceneType", taskRemoteGhDto.getSceneType())
                .queryParam("accessToken",taskRemoteGhDto.getAccessToken())
                .queryParam("examId",taskRemoteGhDto.getExamId())
                .queryParam("getScore",taskRemoteGhDto.getGetScore())
                .queryParam("taskId",taskRemoteGhDto.getTaskId())
                .queryParam("startTime", taskRemoteGhDto.getStartTime())
                .queryParam("endTime", taskRemoteGhDto.getEndTime())
                .queryParam("jsonDetail",JSONObject.toJSONString(taskRemoteGhDto.getJsonDetail()))
                .queryParam("voiceAuthResult",JSONObject.toJSONString(taskRemoteGhDto.getVoiceAuthResponse()));
        httpHeaders.setConnection("Keep-Alive");
        HttpEntity entity = new HttpEntity(httpHeaders);
        int retry = 0;
        String exception = "";
        //重试三次
        while (retry < RETRY_TIMES){
            try{
                LOGGER.info(retry + " --> guanghua param:" + builder.toUriString());
                HttpEntity<String> response = restTemplate.exchange(builder.build().encode().toUri(),HttpMethod.POST,
                        entity,String.class);
                LOGGER.info(retry + "--> guanghua response:"+ JSONArray.toJSONString(response));
                if(response.getBody().contains("\"ret\":\"0\"") || response.getBody().contains("Duplicate entry")){
                    break;
                }
                exception = response.toString();
            }catch (Exception ex){
                LOGGER.info(retry + "--> guanghua exception:" + ex.toString());
                if(ex.toString().contains("Premature EOF")){
                    GuangHuaRecordDto guangHuaRecordDto = new GuangHuaRecordDto();
                    guangHuaRecordDto.setDbName(dbName);
                    guangHuaRecordDto.setData(builder.toUriString());
                    guangHuaRecordDto.setStatus(1);
                    guangHuaRecordDto.setException(exception);
                    guangHuaRecordDto.setStartTime(new Timestamp(System.currentTimeMillis()));
                    guangHuaRecordDto.setUpdateTime(guangHuaRecordDto.getStartTime());
                    guanghuaRecordMapper.saveRecord(guangHuaRecordDto);
                    break;
                }
                exception = ex.toString();
            }
            retry ++;
            //若三次仍推送失败，保留记录和异常原因
            if(retry == RETRY_TIMES){
                GuangHuaRecordDto guangHuaRecordDto = new GuangHuaRecordDto();
                guangHuaRecordDto.setDbName(dbName);
                guangHuaRecordDto.setData(builder.toUriString());
                guangHuaRecordDto.setStatus(1);
                guangHuaRecordDto.setException(exception);
                guangHuaRecordDto.setStartTime(new Timestamp(System.currentTimeMillis()));
                guangHuaRecordDto.setUpdateTime(guangHuaRecordDto.getStartTime());
                guanghuaRecordMapper.saveRecord(guangHuaRecordDto);
            }
        }
    }

}
