package com.zhuiyi.ms.learn.async;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhuiyi.ms.learn.common.DateUtil;
import com.zhuiyi.ms.learn.entity.TChatLog;
import com.zhuiyi.ms.learn.entity.bo.*;
import com.zhuiyi.ms.learn.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Headers;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.*;


/**
 * @author kloazhang
 * @Title: EducationAsync
 * @ProjectName learn
 * @Description: 考试历史异步推送智能教育
 * @date 2019/12/309:49
 */
@Slf4j
@Component
public class EducationAsync {


    public int tryNum = 0;

    @Value("${education.address}")
    private String educationAddress;
    /**
     *  智能教育ip,port
     */
    private String educationHost;

    /**
     *  坐席身份
     */
    private final static Integer SERVICE_FLAG = 1;

    /**
     *  用户身份
     */
    private final static Integer USER_FLAG = 2;


    /**
      * @Description: 异步推送--重试三次
    　* @param ${tags}
    　* @return ${return_type}
    　* @throws
    　* @author kloazhang
    　* @date 2019/12/30 9:52
    */
    @Async("taskExecutor")
//    @Retryable(value = Exception.class,maxAttempts = 3,backoff = @Backoff(delay = 2000,multiplier = 1.5))
    public void pushExam(List<ExamPartBO> partList, String busId) throws Exception{
        tryNum ++;
        Map modelData = getModelId(busId);
        if(null != modelData){
            // 推送智能教育
            log.info("{} num push education", tryNum);
            pushEducation(partList, modelData, busId);
        }
    }

    /**
      * @Description: 获取坐席/模型Id
    　* @param ${tags}
    　* @return ${return_type}
    　* @throws
    　* @author ${USER}
    　* @date 2019/12/30 11:22
    */
    Map getModelId(String busId){
        Map<String,Object> modelData = new HashMap<>();
        String educationUrl = "http://" + educationAddress + "/teach/v1/feature/module/mapping";
        Headers.Builder headers = new Headers.Builder();
        headers.add("X-busId",busId);
        try{
            modelData = (Map) HttpUtil.httpGet(educationUrl,headers.build());
            log.info("get modelBId success {}", JSON.toJSONString(modelData));
        }catch (Exception ex){
            log.info("failed to get modelBId cause: {}", ex.toString());
        }
        return modelData;
    }

    /**
      * @Description: 推送智能教育
    　* @param ${tags}
    　* @return ${return_type}
    　* @throws
    　* @author ${USER}
    　* @date 2019/12/30 11:24
    */
    void pushEducation(List<ExamPartBO> partList,Map modelData, String busId) throws Exception{
        if(CollectionUtils.isNotEmpty(partList)){
            List<EducationModelDataFeatureBO> userDataList = new ArrayList<>();
            List<EducationModelDataFeatureBO> agentDataList = new ArrayList<>();
            for(ExamPartBO examPartBO: partList) {
                for(ExamPartQuestionBO examPartQuestionBO: examPartBO.getQuestionList()) {
                    for(TChatLog tChatLog: examPartQuestionBO.getConversationList()) {
                        Integer identity = tChatLog.getIdentity();
                        //如果内容为空的话，就不需要传这条记录
                        if (identity.equals(SERVICE_FLAG) && StringUtils.isNotEmpty(tChatLog.getContent())) {
                            EducationModelDataFeatureBO userData = dataSort(tChatLog);//组装数据
                            userDataList.add(userData);
                        }
                        if (identity.equals(USER_FLAG) && StringUtils.isNotEmpty(tChatLog.getContent())) {
                            EducationModelDataFeatureBO agentData = dataSort(tChatLog);//组装数据
                            agentDataList.add(agentData);
                        }
                    }
                }
            }
            EducationModuleBO userModule = new EducationModuleBO();
            userModule.setDataFeature(userDataList);
            EducationModuleBO agentModule = new EducationModuleBO();
            agentModule.setDataFeature(agentDataList);
            // 设置用户--坐席模型Bid
            setModuleBId(modelData,userModule,agentModule);
            List<EducationModuleBO> moduleDatas = new ArrayList<>();
            moduleDatas.add(userModule);
            moduleDatas.add(agentModule);
            EducationMetadataVO req = new EducationMetadataVO();
            req.setModuleData(moduleDatas);
            req.setBusId(Integer.valueOf(busId));
            String pushEducationUrl = "http://"+ educationAddress + "/teach/v1/feature/data/import";
            Headers.Builder headers = new Headers.Builder();
            headers.add("X-busId",busId);
            JSONObject response = (JSONObject) HttpUtil.httpPost(pushEducationUrl, JSON.toJSON(req), headers.build());
            log.info("push education success: {}", response.toJSONString());
        }
    }


    EducationModelDataFeatureBO dataSort(TChatLog chatLog) {
        EducationModelDataFeatureBO moduleData = new EducationModelDataFeatureBO();
        moduleData.setName(chatLog.getContent());
        // 0：未训练
        moduleData.setTrained(0);
        // 教育类型。10代表常规教育
        moduleData.setTeachType(10);
        // 数据操作结果。10添加到标签
        moduleData.setHandleResult(10);
        // 来源，10代表Excel带入，20代表后台导入
        moduleData.setSourceType(20);
        if(null != chatLog.getCreateTime()){
            moduleData.setCreatedTime(DateUtil.date2String(chatLog.getCreateTime(),"yyyy-mm-dd HH:MM:SS"));
            moduleData.setUpdatedTime(DateUtil.date2String(chatLog.getCreateTime(),"yyyy-mm-dd HH:MM:SS"));
        }else {
            moduleData.setCreatedTime(DateUtil.date2String(new Date(),"yyyy-mm-dd HH:MM:SS"));
            moduleData.setUpdatedTime(DateUtil.date2String(new Date(),"yyyy-mm-dd HH:MM:SS"));
        }
        // 默认用户--智能教育不关注该字段
        moduleData.setCreatedBy("-1");
        moduleData.setUpdatedBy("-1");
        return moduleData;
    }

    void setModuleBId(Map modelData, EducationModuleBO userModule, EducationModuleBO agentModule){
        try{
            List<Map<String, Object>> bos = (List) modelData.get("data");
            if(null != bos){
                for(Map bidBO : bos){
                    if(StringUtils.isNotEmpty(String.valueOf(bidBO.get("name")))){
                        if(bidBO.get("name").toString().equals("用户模型")){
                            if(StringUtils.isNotEmpty(bidBO.get("long_bid").toString())){
                                userModule.setBId(Long.valueOf(bidBO.get("long_bid").toString()));
                            }else {
                                log.info("get user module Bid is null");
                            }
                        }
                        if(bidBO.get("name").toString().equals("坐席模型")){
                            if(StringUtils.isNotEmpty(bidBO.get("long_bid").toString())){
                                agentModule.setBId(Long.valueOf(bidBO.get("long_bid").toString()));
                            }else {
                                log.info("get service module Bid is null");
                            }
                        }
                    }
                }
            }
        }catch (Exception ex){
            log.info("transfer module Bid error : {} ", ex.toString());
        }
    }

    @Recover
    public  void recover(Exception ex){
        log.info("failed to push education cause: {}", ex.toString());
    }

}
