package com.zhuiyi.ms.learn.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhuiyi.ms.learn.common.DateUtil;
import com.zhuiyi.ms.learn.dto.SessionContextHolder;
import com.zhuiyi.ms.learn.dto.request.*;
import com.zhuiyi.ms.learn.dto.response.ResponseTaskLogsDto;
import com.zhuiyi.ms.learn.dto.response.*;
import com.zhuiyi.ms.learn.dto.response.TaskLogsDto;
import com.zhuiyi.ms.learn.entity.TChatLog;
import com.zhuiyi.ms.learn.entity.bo.ViolationTimesBO;
import com.zhuiyi.ms.learn.entity.vo.res.UnMarkedDataResListVO;
import com.zhuiyi.ms.learn.entity.vo.res.UnMarkedDataVO;
import com.zhuiyi.ms.learn.mapper.TaskLogsInfoMapper;
import com.zhuiyi.ms.learn.service.impl.TChatLogServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author rodbate
 * @Title: TaskLogInfoServiceImpl
 * @ProjectName learn
 * @Description: TODO
 * @date 2018/9/2019:32
 */
@Service
@DS("#session.dataSourceName")
public class TaskLogInfoServiceImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskLogInfoServiceImpl.class);

    @Autowired
    private TaskLogsInfoMapper taskLogsInfoMapper;

    @Resource
    private TChatLogServiceImpl tChatLogService;

    @Value("${educationPageSize}")
    private String educationPageSize;

    /**
      * @Description:  任务历史入库
    　* @author kloazhang
    　* @date 2018/9/20 19:32
    */
    @Transactional(rollbackFor=Exception.class)
    public int saveTaskLogs(RequestTaskLogsDto requestTaskLogsDto) throws  Exception{

        /**
         *  1-培训; 2-冠军挑战赛;3-学习模式
         *  所有模式下的训练任务，sessionId唯一；TaskId存在不唯一性，不作为关联字段
         */
        Map<String,Object> params = new HashMap<>();
        params.put("dbName", SessionContextHolder.getDbName());
        params.put("sessionId", requestTaskLogsDto.getSessionId());
        String dbName = SessionContextHolder.getDbName();
        int result = 0;
        //1.获取请求参数为Session维度，入库
        TaskInfoDto taskInfoDto = new TaskInfoDto();
        taskInfoDto.setQuestionId(requestTaskLogsDto.getQuestionId());
//        taskInfoDto.setTaskId(requestTaskLogsDto.getTaskId());
        taskInfoDto.setTaskType(requestTaskLogsDto.getTaskType());
        taskInfoDto.setSessionId(requestTaskLogsDto.getSessionId());
        taskInfoDto.setServiceId(requestTaskLogsDto.getServiceId());
        taskInfoDto.setServiceName(requestTaskLogsDto.getServiceName());
        taskInfoDto.setScore(requestTaskLogsDto.getScore());
        taskInfoDto.setStatus(requestTaskLogsDto.getStatus());
//        BeanUtils.copyProperties(requestTaskLogsDto, taskInfoDto);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        taskInfoDto.setStartTime(DateUtil.getTimestamp(sdf.parse(requestTaskLogsDto.getStartTime())));
        taskInfoDto.setEndTime(DateUtil.getTimestamp(sdf.parse(requestTaskLogsDto.getEndTime())));
        taskInfoDto.setUpdateTime(DateUtil.getTimestamp(sdf.parse(requestTaskLogsDto.getStartTime())));
        taskInfoDto.setDeleteStatus(requestTaskLogsDto.getDeleteStatus());
        taskInfoDto.setEndType(requestTaskLogsDto.getEndType());
        taskInfoDto.setRemainTime(requestTaskLogsDto.getRemainTime());
        taskInfoDto.setQuestionName(requestTaskLogsDto.getQuestionName());
        taskInfoDto.setDbName(dbName);
        // 默认话术练习
        taskInfoDto.setMode(Optional.ofNullable(requestTaskLogsDto.getMode()).orElse(-1));
        result = taskLogsInfoMapper.saveTaskInfo(taskInfoDto);
        //将写入task_info表生成的主键Id,作为taskId,与chat_log,task_score表字段进行关联写入，查询；
        requestTaskLogsDto.setId(taskInfoDto.getId());
        //2.获取请求参数为log维度，入库
        List<ChatLogDto> chatLogDtoList = new ArrayList<>();
        if(null != requestTaskLogsDto.getConversations().toJSONString()){
            LOGGER.info(requestTaskLogsDto.getConversations().toJSONString());
            chatLogDtoList  = JSONObject.parseArray(requestTaskLogsDto.getConversations().toJSONString(),
                    ChatLogDto.class);
        }
        List<TaskChatLogDto> taskChatLogDtos = new ArrayList<>();
        if(null!= chatLogDtoList && chatLogDtoList.size()>0){
            for(ChatLogDto chatLogDto:chatLogDtoList){
                TaskChatLogDto taskChatLogDto = new TaskChatLogDto();
                //会话流水TaskId对应的是sessionInfo中的id
                taskChatLogDto.setTaskId(requestTaskLogsDto.getId());
                taskChatLogDto.setSessionId(requestTaskLogsDto.getSessionId());
                taskChatLogDto.setChatId(chatLogDto.getChatId());
                taskChatLogDto.setContent(chatLogDto.getContent());
                taskChatLogDto.setIdentity(chatLogDto.getIdentity());
                taskChatLogDto.setStartTimestamp(chatLogDto.getStartTimestamp());
                taskChatLogDto.setEndTimestamp(chatLogDto.getEndTimestamp());
                taskChatLogDto.setIsViolation(null == chatLogDto.getIsViolation() ? 0 : chatLogDto.getIsViolation());
                if(1 == chatLogDto.getIdentity()){
                    taskChatLogDto.setServiceId(requestTaskLogsDto.getServiceId());
                    taskChatLogDto.setServiceName(requestTaskLogsDto.getServiceName());
                }
                if(null != chatLogDto.getGroupId() &&  !chatLogDto.getGroupId().equals("null")){
                    taskChatLogDto.setGroupId(chatLogDto.getGroupId());
                }else {
                    taskChatLogDto.setGroupId(0);
                }
                if(null != chatLogDto.getStartTime()){
                    taskChatLogDto.setCreateTime(DateUtil.getTimestamp(chatLogDto.getStartTime()));
                }
                taskChatLogDtos.add(taskChatLogDto);
            }
        }
        if(taskChatLogDtos.size()>0){
            params.put("chatLogList", taskChatLogDtos);
            result += taskLogsInfoMapper.batchInsertChatLog(params);
        }
        //3.获取请求为checkResult结果，入库
        List<CheckRulesDto> checkRulesDtos = new ArrayList<>();
        if(null!=requestTaskLogsDto.getCheckRules()){
            LOGGER.info(requestTaskLogsDto.getCheckRules().toJSONString());
            checkRulesDtos = JSONObject.parseArray(requestTaskLogsDto.getCheckRules().toJSONString(),
                    CheckRulesDto.class);
        }
        List<TaskScoreDto> taskScores = new ArrayList<>();
        if(null != checkRulesDtos && checkRulesDtos.size()>0){
            for(CheckRulesDto checkRulesDto : checkRulesDtos){
                TaskScoreDto taskScoreDto = new TaskScoreDto();
                //历史得分taskId对应的是sessionInfo中的id
                taskScoreDto.setTaskId(requestTaskLogsDto.getId());
                taskScoreDto.setScoreType(checkRulesDto.getScoreType());
                taskScoreDto.setQuestionName(requestTaskLogsDto.getQuestionName());
                taskScoreDto.setNodeId(checkRulesDto.getNodeId());
                taskScoreDto.setNodeName(checkRulesDto.getNodeName());
                taskScoreDto.setRuleId(checkRulesDto.getRuleId());
                taskScoreDto.setRuleName(checkRulesDto.getRuleName());
                taskScoreDto.setScore(checkRulesDto.getScore());
                taskScoreDto.setDescription(checkRulesDto.getDesc());
                taskScoreDto.setChatId(checkRulesDto.getChatIds().toString());
                taskScoreDto.setViolationStatus(checkRulesDto.getIsViolation());
                taskScoreDto.setProcessRetryLen(checkRulesDto.getProcessRetryLen());
                taskScores.add(taskScoreDto);
            }
        }
        if(taskScores.size()>0){
            params.clear();
            params.put("dbName", SessionContextHolder.getDbName());
            params.put("taskScoreList", taskScores);
            result += taskLogsInfoMapper.batchInsertTaskScore(params);
        }
        return  result;
    }

    /**
      * @Description:  查询历史任务
    　* @author kloazhang
    　* @date 2018/9/20 23:01
    */
    public ResponseVO getTaskLogs(RequestTaskLogsDto requestTaskLogsDto) throws  Exception{

        String dbName = SessionContextHolder.getDbName();
        ResponseVO responseVO = new ResponseVO();
        //1.查询task/session
        List<ResponseTaskLogsDto> responseTaskLogsDtos = new ArrayList<>();
        String searchType = "all";
        if(null != requestTaskLogsDto.getSearchType() && requestTaskLogsDto.getSearchType().equals(searchType)){
            Map<String,Object> params = new HashMap<>();
            params.put("dbName", dbName);
            responseTaskLogsDtos = taskLogsInfoMapper.getTaskInfos(params);
            if(responseTaskLogsDtos.size()>0){
                for(ResponseTaskLogsDto responseTaskLogsDto: responseTaskLogsDtos){
                    if(null != responseTaskLogsDto.getStartTimeDate()){
                        String createTime = DateUtil.date2String(responseTaskLogsDto.getStartTimeDate(),
                                "yyyy-MM-dd HH:mm:ss");
                        responseTaskLogsDto.setStartTime(createTime);
                    }
                    if(null!= responseTaskLogsDto.getEndTimeDate()){
                        String endTime = DateUtil.date2String(responseTaskLogsDto.getEndTimeDate(),
                                "yyyy-MM-dd HH:mm:ss");
                        responseTaskLogsDto.setEndTime(endTime);
                    }
                }
            }
            //2.遍历查询log
            List<TaskLogsDto> taskLogsDtoList = new ArrayList<>();
            if(responseTaskLogsDtos.size() > 0 ){
                for(ResponseTaskLogsDto responseTaskLogsDto:responseTaskLogsDtos){
                    List<TaskLogsDto> taskLogsDtos = new ArrayList<>();
                    params.put("id", responseTaskLogsDto.getId());
                    params.put("dbName", dbName);
                    taskLogsDtos = taskLogsInfoMapper.getTaskLogs(params);
                    if(taskLogsDtos.size()>0){
                        for(TaskLogsDto taskLogsDto:taskLogsDtos){
                            if(null != taskLogsDto.getCreateTimeDate()){
                                String createTime = DateUtil.date2String(taskLogsDto.getCreateTimeDate(),
                                        "yyyy-MM-dd HH:mm:ss");
                                taskLogsDto.setCreateTime(createTime);
                            }
                        }
                    }
                    responseTaskLogsDto.setConversationList(taskLogsDtos);
                    taskLogsDtoList.addAll(taskLogsDtos);
                }
            }
            //3.遍历log查询score
            if(taskLogsDtoList.size()>0){
                for(ResponseTaskLogsDto responseTaskLogsDto:responseTaskLogsDtos){
                    List<ResponseTaskScoreDto> taskScoreDtoList = new ArrayList<>();
                    if(responseTaskLogsDto.getConversationList().size()>0){
                        for(TaskLogsDto taskLogsDto:responseTaskLogsDto.getConversationList()){
                            params.put("id", responseTaskLogsDto.getId());
                            params.put("dbName", dbName);
                            taskScoreDtoList = taskLogsInfoMapper.getTaskScore(params);
                            //按照ScoreType进行排序，再按照nodeId对流程节点进行排序
                            taskScoreDtoList.sort(Comparator.comparing(ResponseTaskScoreDto::getScoreType).
                                    thenComparing(ResponseTaskScoreDto::getNodeId));
                        }
                    }
                    responseTaskLogsDto.setCheckRules(taskScoreDtoList);
                }
            }
        }
        else{
            Map<String,Object> params = new HashMap<>();
            params.put("sessionId", requestTaskLogsDto.getSessionId());
            params.put("taskId",requestTaskLogsDto.getTaskId());
            params.put("taskType",requestTaskLogsDto.getTaskType());
            params.put("startTime", requestTaskLogsDto.getStartTime());
            params.put("endTime",requestTaskLogsDto.getEndTime() );
            params.put("userId", requestTaskLogsDto.getUserId());
            params.put("dbName", dbName);
            List<Integer> taskIds = new ArrayList<>();
            responseTaskLogsDtos = taskLogsInfoMapper.getTaskInfos(params);
            if(responseTaskLogsDtos.size()>0){
                for(ResponseTaskLogsDto responseTaskLogsDto: responseTaskLogsDtos){
                    if(null != responseTaskLogsDto.getStartTimeDate()){
                        String createTime = DateUtil.date2String(responseTaskLogsDto.getStartTimeDate(),
                                "yyyy-MM-dd HH:mm:ss");
                        responseTaskLogsDto.setStartTime(createTime);
                    }
                    if(null!= responseTaskLogsDto.getEndTimeDate()){
                        String endTime = DateUtil.date2String(responseTaskLogsDto.getEndTimeDate(),
                                "yyyy-MM-dd HH:mm:ss");
                        responseTaskLogsDto.setEndTime(endTime);
                    }
                    taskIds.add(responseTaskLogsDto.getId());
                }
            }
            //2.遍历查询log
            if(CollectionUtils.isNotEmpty(taskIds)){
                params.put("taskIds", taskIds);
                List<TaskLogsDto> taskLogsDtos = new ArrayList<>();
                List<ResponseTaskScoreDto> taskScoreDtoList = new ArrayList<>();
                taskLogsDtos = taskLogsInfoMapper.getTaskLogs(params);
                taskScoreDtoList = taskLogsInfoMapper.getTaskScore(params);
                for(ResponseTaskLogsDto responseTaskLogsDto: responseTaskLogsDtos){
                    List<TaskLogsDto> converSation = new ArrayList<>();
                    List<ResponseTaskScoreDto> taskScoreDtos = new ArrayList<>();
                    for(TaskLogsDto taskLogsDto : taskLogsDtos){
                        if(null != taskLogsDto.getCreateTimeDate()){
                            String createTime = DateUtil.date2String(taskLogsDto.getCreateTimeDate(),
                                    "yyyy-MM-dd HH:mm:ss");
                            taskLogsDto.setCreateTime(createTime);
                        }
                        if(taskLogsDto.getTaskId() == responseTaskLogsDto.getId()){
                            converSation.add(taskLogsDto);
                        }
                    }
                    for(ResponseTaskScoreDto responseTaskScoreDto : taskScoreDtoList){
                        if(responseTaskScoreDto.getTaskId() == responseTaskLogsDto.getId()){
                            taskScoreDtos.add(responseTaskScoreDto);
                        }
                    }
                    responseTaskLogsDto.setConversationList(converSation);
                    //按照ScoreType进行排序，再按照nodeId对流程节点进行排序
                    taskScoreDtos.sort(Comparator.comparing(ResponseTaskScoreDto::getScoreType).
                            thenComparing(ResponseTaskScoreDto::getNodeId));
                    responseTaskLogsDto.setCheckRules(taskScoreDtos);
                }
            }


//            List<TaskLogsDto> taskLogsDtoList = new ArrayList<>();
//            if(responseTaskLogsDtos.size() > 0 ){
//                for(ResponseTaskLogsDto responseTaskLogsDto:responseTaskLogsDtos){
//                    List<TaskLogsDto> taskLogsDtos = new ArrayList<>();
//                    params.put("id", responseTaskLogsDto.getId());
//                    params.put("dbName", dbName);
//                    taskLogsDtos = taskLogsInfoMapper.getTaskLogs(params);
//                    if(taskLogsDtos.size()>0){
//                        for(TaskLogsDto taskLogsDto:taskLogsDtos){
//                            if(null != taskLogsDto.getCreateTimeDate()){
//                                String createTime = DateUtil.date2String(taskLogsDto.getCreateTimeDate(),
//                                        "yyyy-MM-dd HH:mm:ss");
//                                taskLogsDto.setCreateTime(createTime);
//                            }
//                        }
//                    }
//                    responseTaskLogsDto.setConversationList(taskLogsDtos);
//                    taskLogsDtoList.addAll(taskLogsDtos);
//                }
//            }
//            //3.遍历log查询score
//            if(taskLogsDtoList.size()>0){
//                for(ResponseTaskLogsDto responseTaskLogsDto:responseTaskLogsDtos){
//                    List<ResponseTaskScoreDto> taskScoreDtoList = new ArrayList<>();
//                    if(responseTaskLogsDto.getConversationList().size()>0){
//                        for(TaskLogsDto taskLogsDto:responseTaskLogsDto.getConversationList()){
//                            params.put("id", responseTaskLogsDto.getId());
//                            params.put("dbName", dbName);
//                            taskScoreDtoList = taskLogsInfoMapper.getTaskScore(params);
//                            //按照ScoreType进行排序，再按照nodeId对流程节点进行排序
//                            taskScoreDtoList.sort(Comparator.comparing(ResponseTaskScoreDto::getScoreType).
//                                    thenComparing(ResponseTaskScoreDto::getNodeId));
//                        }
//                    }
//                    responseTaskLogsDto.setCheckRules(taskScoreDtoList);
//                }
//            }
        }
        responseVO.setData(responseTaskLogsDtos);
        return  responseVO;
    }

    /**
      * @Description:  根据日期统计违规节点数-扣分项统计
    　* @author kloazhang
    　* @date 2018/9/21 13:30
    */
    public ResponseVO getTotalViolationNodes(RequestViolationNodesDto requestViolationNodesDto){

        String dbName = SessionContextHolder.getDbName();
        Map<String,Object> params = new HashMap<>();
        params.put("startTime", requestViolationNodesDto.getStartTime());
        params.put("endTime",requestViolationNodesDto.getEndTime());
        params.put("scoreType",requestViolationNodesDto.getScoreType());
        params.put("violationStatus", requestViolationNodesDto.getViolationStatus() == 0 ? 1 :
                requestViolationNodesDto.getViolationStatus());
        params.put("serviceIds",requestViolationNodesDto.getServiceIds());
        params.put("dbName", dbName);
        List<ResponseTotalNodeDto> responseTotalNodeDtos = new ArrayList<>();
        if(1==requestViolationNodesDto.getScoreType()){
            responseTotalNodeDtos = taskLogsInfoMapper.getCountViolationsByNode(params);
            if(null != responseTotalNodeDtos && responseTotalNodeDtos.size()>0){
                for(ResponseTotalNodeDto responseTotalNodeDto:responseTotalNodeDtos){
                    responseTotalNodeDto.setDateSign(DateUtil.date2String(responseTotalNodeDto.getCreateTime(),
                            "yyyy-MM-dd"));
                }
            }
        }if(2==requestViolationNodesDto.getScoreType()){
            responseTotalNodeDtos = taskLogsInfoMapper.getCountViolationsByGlobal(params);
            if(null != responseTotalNodeDtos && responseTotalNodeDtos.size()>0){
                for(ResponseTotalNodeDto responseTotalNodeDto:responseTotalNodeDtos){
                    responseTotalNodeDto.setDateSign(DateUtil.date2String(responseTotalNodeDto.getCreateTime(),
                            "yyyy-MM-dd"));
                }
            }
        }
        ResponseVO responseVO = new ResponseVO();
        responseVO.setData(responseTotalNodeDtos);
        return  responseVO;
    }

    /**
      * @Description:  坐席分数统计
    　* @author kloazhang
    　* @date 2018/9/21 15:41
    */
    public ResponseVO getTotalServiceScore(RequestServiceScoreDto requestServiceScoreDto) {

        String dbName = SessionContextHolder.getDbName();
        ResponseVO responseVO = new ResponseVO();
        List<ResponseServiceScoreDto> responseServiceScoreDtos = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        int currentPage = 0;
        if (requestServiceScoreDto.getCurrentPage() > 0) {
            currentPage = requestServiceScoreDto.getCurrentPage() - 1;
        }
        int pageSize = requestServiceScoreDto.getPageSize();
        if (pageSize <= 0) {
            pageSize = 10;
        }
        requestServiceScoreDto.setLimit(pageSize);
        requestServiceScoreDto.setOffset(currentPage * pageSize);
        requestServiceScoreDto.setTaskType(requestServiceScoreDto.getTaskType() == 0 ?
                1 : requestServiceScoreDto.getTaskType());
        requestServiceScoreDto.setDbName(dbName);
        Integer totalNums = taskLogsInfoMapper.getTotalServiceScore(requestServiceScoreDto);
        if(null == totalNums){
            totalNums = 0;
        }
        int allPages = (totalNums + pageSize - 1) / pageSize;
        responseVO.setTotal(totalNums);
        responseVO.setAllPages(allPages);
        responseVO.setPageSize(pageSize == 0 ? 1 : pageSize);
        responseVO.setCurrentPage(requestServiceScoreDto.getCurrentPage() == 0 ? 1 : requestServiceScoreDto.getCurrentPage());
        if (totalNums > 0) {
            //1.获取统计元数据
            responseServiceScoreDtos = taskLogsInfoMapper.
                    getServiceTotalScoreDto(requestServiceScoreDto);
            //2.遍历不同客服名称，得到其下的taskId
            if (null != responseServiceScoreDtos && responseServiceScoreDtos.size() > 0) {

                requestServiceScoreDto.setServiceIds(responseServiceScoreDtos.stream().map(item -> item.getServiceId()).collect(Collectors.toList()));
                List<TotalTaskInfoDto> totalTaskInfoDtos = taskLogsInfoMapper.getTaskIdAndService(requestServiceScoreDto);
                Map<String, List<Integer>> serviceId2IdList = new HashMap<>(500);
                for(TotalTaskInfoDto taskInfoDto: totalTaskInfoDtos) {
                    List<Integer> idList = serviceId2IdList.get(taskInfoDto.getServiceId());
                    if (idList == null) {
                        serviceId2IdList.put(taskInfoDto.getServiceId(), new ArrayList<>(Arrays.asList(taskInfoDto.getId())));
                    } else {
                        idList.add(taskInfoDto.getId());
                    }

                }
                List<Integer> taskIdList = totalTaskInfoDtos.stream().map(item -> item.getId()).collect(Collectors.toList());
                if (!taskIdList.isEmpty()) {
                    Map<Integer, Integer> id2processViolationTimes = new HashMap<>(200);
                    List<ViolationTimesBO> processViolationTimesList = taskLogsInfoMapper.getViolationTimesByTaskIdList(1, taskIdList, dbName);
                    for(ViolationTimesBO violationTimesBO: processViolationTimesList) {
                        id2processViolationTimes.put(violationTimesBO.getId(), violationTimesBO.getViolationTimes());
                    }

                    Map<Integer, Integer> id2globalViolationTimes = new HashMap<>(200);
                    List<ViolationTimesBO> globalViolationTimesList = taskLogsInfoMapper.getViolationTimesByTaskIdList(2, taskIdList, dbName);
                    for(ViolationTimesBO violationTimesBO: globalViolationTimesList) {
                        id2globalViolationTimes.put(violationTimesBO.getId(), violationTimesBO.getViolationTimes());
                    }

                    for(ResponseServiceScoreDto responseServiceScoreDto: responseServiceScoreDtos) {
                        int processViolationTimes = 0;
                        int globalViolationTimes = 0;
                        for(Integer id: serviceId2IdList.get(responseServiceScoreDto.getServiceId())) {
                            processViolationTimes += id2processViolationTimes.getOrDefault(id, 0);
                            globalViolationTimes += id2globalViolationTimes.getOrDefault(id, 0);
                        }
                        responseServiceScoreDto.setProcessViolationTimes(processViolationTimes);
                        responseServiceScoreDto.setGlobalViolationTimes(globalViolationTimes);
                    }

                }
            }
        }

        if (requestServiceScoreDto.getMaxScore() == null) {
            requestServiceScoreDto.setMaxScore(1000f);
        }

        if (requestServiceScoreDto.getMinScore() == null) {
            requestServiceScoreDto.setMinScore(-1000f);
        }

        responseVO.setData(responseServiceScoreDtos.stream().filter(item -> item.getAverageScore() < requestServiceScoreDto.getMaxScore()
                && item.getAverageScore() > requestServiceScoreDto.getMinScore()).collect(Collectors.toList()));
        return responseVO;
    }

    public ResponseVO getAllservice(){

        ResponseVO responseVO = new ResponseVO();
        String dbName= SessionContextHolder.getDbName();
        responseVO.setData(taskLogsInfoMapper.getAllService(dbName));
        return  responseVO;
    }

    public Integer deleteTaskLog(RequestTaskLogsDto requestTaskLogsDto){

        String dbName = SessionContextHolder.getDbName();
        int result = 0;
        if(null != requestTaskLogsDto.getSessionId()){
            result = taskLogsInfoMapper.deleteTaskLogBySessionId(requestTaskLogsDto.getSessionId(),dbName);
        }
        return  result;
    }

    /**
      * @Description:  查询历史任务
    　* @author kloazhang
    　* @date 2018/9/20 23:01
     */
    public HashMap<String, List> getAllTaskLogs(RequestTaskLogsDto requestTaskLogsDto) {
        Page page = new Page();
        page.setCurrent(requestTaskLogsDto.getCurrentPage());
        page.setSize(requestTaskLogsDto.getPageSize());

        IPage<TChatLog> iPage = tChatLogService.page(page);

        UnMarkedDataResListVO unMarkedDataResVO = new UnMarkedDataResListVO();
        unMarkedDataResVO.setConversationList(iPage.getRecords().stream().map(item -> {
            UnMarkedDataVO unMarkedDataVO = new UnMarkedDataVO();
            unMarkedDataVO.setContent(item.getContent());
            unMarkedDataVO.setIdentity(item.getIdentity());

            return unMarkedDataVO;
        }).collect(Collectors.toList()));

        HashMap<String, List> res = new HashMap<>();
        res.put("data", new ArrayList<>(Arrays.asList(unMarkedDataResVO)));
        return res;
    }

    public ResponseVO  getAllTaskLogsCount(){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setTotal(tChatLogService.count());
        return responseVO;
    }

}
