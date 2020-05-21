package com.zhuiyi.ms.learn.async;

import com.alibaba.fastjson.JSONObject;
import com.zhuiyi.ms.learn.dto.request.GhTaskScoreDto;
import com.zhuiyi.ms.learn.dto.request.RequestTaskLogsDto;
import com.zhuiyi.ms.learn.dto.request.TaskRemoteGhDto;
import com.zhuiyi.ms.learn.dto.response.GhJsonDetailDto;
import com.zhuiyi.ms.learn.dto.response.ListJsonDetailDto;
import com.zhuiyi.ms.learn.dto.response.VoiceAuthResponse;
import com.zhuiyi.ms.learn.mapper.TaskLogsInfoMapper;
import com.zhuiyi.ms.learn.proxy.RemoteCglibProxy;
import com.zhuiyi.ms.learn.service.GuanghuaRemoteService;
import com.zhuiyi.ms.learn.service.VoiceAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author rodbate
 * @Title: TaskInfoAsyc
 * @ProjectName learn
 * @Description: TODO
 * @date 2019/2/2714:11
 */
@Component
public class TaskInfoAsync {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskInfoAsync.class);

    private static final Integer RETRY_TIMES = 3;

    @Autowired
    private VoiceAuth voiceAuth;

    @Autowired
    private GuanghuaRemoteService guanghuaRemoteService;

    @Autowired
    private TaskLogsInfoMapper taskLogsInfoMapper;

    @Async("taskExecutor")
    public void restXfGh(RequestTaskLogsDto requestTaskLogsDto){
        long start = System.currentTimeMillis();
        LOGGER.info(Thread.currentThread().getName());
        VoiceAuth proxyService = (VoiceAuth) new RemoteCglibProxy().getProxy(VoiceAuth.class);
        VoiceAuthResponse voiceAuthResponse;
        try{
            //上报讯飞，获取声纹认证结果
            voiceAuthResponse = proxyService.remoteCall(requestTaskLogsDto);
        }catch (Exception ex){
            LOGGER.info("voiceAuthToXunfei Error-->" + ex.toString());
            //00xx：重试两次网络连接失败;
            voiceAuthResponse = new VoiceAuthResponse("0","ZY0001","声纹认证接口重试超时");
        }
        LOGGER.info(voiceAuthResponse.toString());
        Map<String,Object> params = new HashMap<>();
        params.put("sessionId", requestTaskLogsDto.getSessionId());
        params.put("dbName", requestTaskLogsDto.getDbName());
        params.put("voiceAuthResult", voiceAuthResponse.getStatus());
        params.put("voiceAuthScore", voiceAuthResponse.getScore());
        //更新java数据t_task_info；关于该条历史记录得声纹认证结果；
        int result = taskLogsInfoMapper.updateSessionInfo(params);
        if(result == 0){
            LOGGER.info("无该Task历史记录，请核对...");
        }
        //调用光华接口
        TaskRemoteGhDto taskRemoteGhDto = new TaskRemoteGhDto();
        //taken来自界面前端
        taskRemoteGhDto.setAccessToken(requestTaskLogsDto.getUserData().getAccessToken());
        //与声纹认证采用统一注册标识
        taskRemoteGhDto.setFrontUserId(requestTaskLogsDto.getUserData().getFrontUserId());
        //固定值为“cpic”
        taskRemoteGhDto.setCoopCode("cpic");
        //通关考Id即为taskId
        taskRemoteGhDto.setExamId(requestTaskLogsDto.getQuestionId());
        taskRemoteGhDto.setTaskId(requestTaskLogsDto.getTaskId());
        taskRemoteGhDto.setGetScore(requestTaskLogsDto.getScore());
        taskRemoteGhDto.setStartTime(requestTaskLogsDto.getStartTime());
        taskRemoteGhDto.setEndTime(requestTaskLogsDto.getEndTime());
        //讯飞声纹认证结果
        taskRemoteGhDto.setVoiceAuthResponse(voiceAuthResponse);
        //维度字段，有待确定
        List<GhTaskScoreDto> ghTaskScoreDtos = new ArrayList<>();
        if(null!=requestTaskLogsDto.getCheckRules()){
            LOGGER.info(requestTaskLogsDto.getCheckRules().toJSONString());
            ghTaskScoreDtos = JSONObject.parseArray(requestTaskLogsDto.getCheckRules().toJSONString(),
                    GhTaskScoreDto.class);
        }
        //根据categoryName分组
        Map<String,List<GhTaskScoreDto>> groupByCategoryName = ghTaskScoreDtos.stream().collect
                (Collectors.groupingBy(GhTaskScoreDto::getCategoryName));
        List<GhJsonDetailDto> categoryList = new ArrayList<>();
        if(null != groupByCategoryName && groupByCategoryName.size() > 0){
            for(Map.Entry<String,List<GhTaskScoreDto>> ghTaskScore : groupByCategoryName.entrySet()){
                String categoryName = ghTaskScore.getKey();
                //获取相同categoryName的规则分类集合
                List<GhTaskScoreDto> ghTaskCheckRules = ghTaskScore.getValue();
                GhJsonDetailDto ghJsonDetailDto = new GhJsonDetailDto();
                ghJsonDetailDto.setCategoryName(categoryName);
                int categoryGetScore = 0;
                int categoryTotalScore = 0;
                for(GhTaskScoreDto ghTaskScoreDto : ghTaskCheckRules){
                    //获取该相同categoryName的score总分
                    categoryTotalScore += ghTaskScoreDto.getScore();
                    if(ghTaskScoreDto.getIsViolation() == 1){
                        //获取该相同categoryName未违规得分总分
                        categoryGetScore += ghTaskScoreDto.getScore();
                    }
                }
                ghJsonDetailDto.setCategoryTotalScore(categoryTotalScore);
                ghJsonDetailDto.setCategoryGetScore(categoryGetScore);
                categoryList.add(ghJsonDetailDto);
            }
        }
        ListJsonDetailDto jsonDetail = new ListJsonDetailDto();
        jsonDetail.setCategoryList(categoryList);
        taskRemoteGhDto.setJsonDetail(jsonDetail);
        taskRemoteGhDto.setVoiceAuthToGuangHua(requestTaskLogsDto.getVoiceAuthToGuangHua());
        taskRemoteGhDto.setSceneName(requestTaskLogsDto.getQuestionName());
        taskRemoteGhDto.setSceneType(requestTaskLogsDto.getQuestionType());
        taskRemoteGhDto.setSceneId(requestTaskLogsDto.getQuestionId());
        taskRemoteGhDto.setSceneTypeId(requestTaskLogsDto.getQuestionTypeId());
        retryToGuanghua(taskRemoteGhDto,requestTaskLogsDto.getDbName());
        long end = System.currentTimeMillis();
        LOGGER.info("声纹认证+保存通关考耗时: "+ (end -start) + "ms");
    }


    public void voiceToGh(TaskRemoteGhDto taskRemoteGhDto){

        GuanghuaRemoteService proxyService = (GuanghuaRemoteService) new RemoteCglibProxy().getProxy(GuanghuaRemoteService.class);
        String baseResponse;
        try{
            //上报讯飞，获取声纹认证结果
            baseResponse = proxyService.remoteCall(taskRemoteGhDto);
        }catch (Exception ex){
            LOGGER.info("GuanghuaRemoteService-->" + ex.toString());
            //00xx：重试两次网络连接失败;
            baseResponse = "重试光华接口两次超时异常";
        }
        LOGGER.info("推送通关考成绩至光华>>>" + baseResponse);
    }


    /**
      * @Description: 重试三次，保留记录
    　* @param ${tags}
    　* @return ${return_type}
    　* @throws
    　* @author ${USER}
    　* @date 2019/5/17 15:05
    */
    public void retryToGuanghua(TaskRemoteGhDto taskRemoteGhDto,String dbName){

        long start = System.currentTimeMillis();
        guanghuaRemoteService.retryCall(taskRemoteGhDto,dbName);
        long end = System.currentTimeMillis();
        LOGGER.info("post guanghua Times:"+ (end - start));
    }

}
