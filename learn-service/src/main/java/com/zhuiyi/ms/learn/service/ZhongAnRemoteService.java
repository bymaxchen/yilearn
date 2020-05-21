package com.zhuiyi.ms.learn.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhuiyi.ms.learn.dto.request.GuangHuaRecordDto;
import com.zhuiyi.ms.learn.dto.request.TaskRemoteGhDto;
import com.zhuiyi.ms.learn.dto.request.api.ZhongAnReqDto;
import com.zhuiyi.ms.learn.mapper.GuanghuaRecordMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
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
public class ZhongAnRemoteService {


    private static final Logger LOGGER = LoggerFactory.getLogger(ZhongAnRemoteService.class);

    @Autowired
    private GuanghuaRecordMapper guanghuaRecordMapper;

    public void retryCall(ZhongAnReqDto zhongAnReqDto, String dbName) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        try{
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(zhongAnReqDto.getZhongAnUrl())
                    .queryParam("accessToken",zhongAnReqDto.getAccessToken());
            LOGGER.info("zhongan url:" + JSON.toJSONString(builder.build().toUri()));
            LOGGER.info("zhongan params:" + JSON.toJSONString(zhongAnReqDto));
            HttpEntity<String> response = restTemplate.postForEntity(builder.build().toUri(), zhongAnReqDto, String.class);
            LOGGER.info("zhongan response:"+ JSONArray.toJSONString(response));
            //返回报错，记录数据，待人工补偿
            if(response.getBody().contains("\"status\":\"ERROR\"")){
                LOGGER.info(response.getBody());
                GuangHuaRecordDto guangHuaRecordDto = new GuangHuaRecordDto();
                guangHuaRecordDto.setDbName(dbName);
                guangHuaRecordDto.setData(JSON.toJSONString(zhongAnReqDto));
                guangHuaRecordDto.setStatus(1);
                guangHuaRecordDto.setException(response.getBody());
                guangHuaRecordDto.setStartTime(new Timestamp(System.currentTimeMillis()));
                guangHuaRecordDto.setUpdateTime(guangHuaRecordDto.getStartTime());
                guanghuaRecordMapper.saveRecord(guangHuaRecordDto);
            }
        }catch (Exception ex){
            //若程序内部异常， 记录这条推送众安的历史数据
            LOGGER.info(ex.toString());
            GuangHuaRecordDto guangHuaRecordDto = new GuangHuaRecordDto();
            guangHuaRecordDto.setDbName(dbName);
            guangHuaRecordDto.setData(JSON.toJSONString(zhongAnReqDto));
            guangHuaRecordDto.setStatus(1);
            guangHuaRecordDto.setException(ex.toString().substring(0, 100));
            guangHuaRecordDto.setStartTime(new Timestamp(System.currentTimeMillis()));
            guangHuaRecordDto.setUpdateTime(guangHuaRecordDto.getStartTime());
            guanghuaRecordMapper.saveRecord(guangHuaRecordDto);
        }

    }

}
