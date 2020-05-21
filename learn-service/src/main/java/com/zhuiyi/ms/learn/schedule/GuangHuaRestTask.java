//package com.zhuiyi.ms.learn.schedule;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.zhuiyi.ms.learn.dto.request.GuangHuaRecordDto;
//import com.zhuiyi.ms.learn.mapper.GuanghuaRecordMapper;
//import org.apache.commons.collections.CollectionUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.MediaType;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//
//import java.net.URI;
//import java.sql.Timestamp;
//import java.util.*;
//
///**
// * @author rodbate
// * @Title: GuangHuaRestTask
// * @ProjectName learn
// * @Description: TODO
// * @date 2019/5/1716:30
// */
//@Component
//public class GuangHuaRestTask {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(GuangHuaRestTask.class);
//
//    private static final Integer RETRY_TIMES = 3;
//
//    @Autowired
//    private GuanghuaRecordMapper guanghuaRecordMapper;
//
//    @Scheduled(cron = "0 */10 * * * ?")
//    public void retryPost(){
//        List<GuangHuaRecordDto> guangHuaRecordDtoList = guanghuaRecordMapper.getFailRecord();
//        if(CollectionUtils.isNotEmpty(guangHuaRecordDtoList)){
//            /**
//             *  每条推送光华失败的记录。重试三次：
//             *  1。 推送成功后，删除该条记录
//             *  2。 推送失败，仍保留该条记录
//             */
//            HttpHeaders httpHeaders = new HttpHeaders();
//            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//            HttpEntity entity = new HttpEntity(httpHeaders);
//            for(GuangHuaRecordDto guangHuaRecordDto:guangHuaRecordDtoList){
//                String data = guangHuaRecordDto.getData();
//                RestTemplate restTemplate = new RestTemplate();
//                int retry = 0;
//                String exception = "";
//                //重试三次
//                Map<String,Object> params = new HashMap<>();
//                //发送中
//                params.put("status", 1);
//                params.put("id", guangHuaRecordDto.getId());
//                //判断当前记录是否呗其他现场占用
//                if(guanghuaRecordMapper.updateRecordStatus(params) > 0){
//                    while (retry < RETRY_TIMES){
//                        try{
//                            URI uri = new URI(data);
//                            LOGGER.info(retry + "--> guanghua param:" + JSON.toJSONString(data));
//                            HttpEntity<String> response = restTemplate.exchange(uri,HttpMethod.POST,
//                                    entity,String.class);
//                            LOGGER.info(retry + "--> guanghua response:"+ JSONArray.toJSONString(response));
//                            //推送成功，排除重复推送报错;表里清除此记录
//                            if(response.getBody().contains("\"ret\":\"0\"") || response.getBody().contains("Duplicate entry")){
//                                guanghuaRecordMapper.deleteRecord(guangHuaRecordDto.getId());
//                                break;
//                            }
//                            exception = response.toString();
//                        }catch (Exception ex){
//                            LOGGER.info(retry + "--> guanghua exception:" + ex.toString());
//                            //光华端EOF--默认已推送成功
//                            if(ex.toString().contains("Premature EOF")){
////                                guanghuaRecordMapper.deleteRecord(guangHuaRecordDto.getId());
//                                guangHuaRecordDto.setStatus(1);
//                                guangHuaRecordDto.setException(exception);
//                                guangHuaRecordDto.setUpdateTime(new Timestamp(System.currentTimeMillis()));
//                                guanghuaRecordMapper.updateRecord(guangHuaRecordDto);
//                                break;
//                            }
//                            exception = ex.toString();
//                        }
//                        retry ++;
//                        //若三次仍推送失败，更新当条记录失败原因
//                        if(retry == RETRY_TIMES){
//                            //将发送至状态改为发送失败
//                            guangHuaRecordDto.setStatus(1);
//                            guangHuaRecordDto.setException(exception);
//                            guangHuaRecordDto.setUpdateTime(new Timestamp(System.currentTimeMillis()));
//                            guanghuaRecordMapper.updateRecord(guangHuaRecordDto);
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
