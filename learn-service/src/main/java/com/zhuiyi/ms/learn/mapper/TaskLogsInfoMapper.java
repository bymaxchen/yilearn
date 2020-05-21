package com.zhuiyi.ms.learn.mapper;

import com.zhuiyi.ms.learn.dto.request.RequestServiceScoreDto;
import com.zhuiyi.ms.learn.dto.request.TaskChatLogDto;
import com.zhuiyi.ms.learn.dto.request.TaskInfoDto;
import com.zhuiyi.ms.learn.dto.request.TaskScoreDto;
import com.zhuiyi.ms.learn.dto.response.ResponseTaskLogsDto;
import com.zhuiyi.ms.learn.dto.response.*;
import com.zhuiyi.ms.learn.entity.TaskScore;
import com.zhuiyi.ms.learn.entity.bo.ViolationTimesBO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author rodbate
 * @Title: TaskLogsInfoMapper
 * @ProjectName learn
 * @Description: TODO
 * @date 2018/9/2019:36
 */
@Mapper
public interface TaskLogsInfoMapper {


    List<ViolationTimesBO> getViolationTimesByTaskIdList(@Param("scoreType") Integer scoreType, @Param("taskIdList") List taskIdList, @Param("dbName") String dbName);
    /**
     *  获取TaskId对应的Id，用于重新学习时判断相同历史入库
     */
    Integer getTaskBelongId(Map<String,Object> params);

    /**
     *  更新sessionInfo
     */
    int updateSessionInfo(Map<String,Object> params);

    /**
     *  软删除历史会话
     */
     int deleteChatLogById(Map<String,Object> params);

    /**
     *  软删除历史得分
     */
    int deleteTaskScore(Map<String,Object> params);

    /**
     *
     * @param task,session维度入库
     * @return
     */
    int saveTaskInfo(TaskInfoDto taskInfoDto);

    /**
     * log维度入库
     */
    int saveChatLog(TaskChatLogDto chatLogDto);

    //3.result入库
    int saveTaskScore(TaskScoreDto taskScoreDto);

    //4. 查询task/session
    List<ResponseTaskLogsDto> getTaskInfos(Map<String,Object> params);

    //5.查询log
    List<TaskLogsDto> getTaskLogs(Map<String,Object> params);

    //查询result
    List<ResponseTaskScoreDto> getTaskScore(Map<String,Object> params);

    //根据日期统计触发得节点违规次数
    List<ResponseTotalNodeDto> getCountViolationsByNode(Map<String,Object> params);
    List<ResponseTotalNodeDto> getCountViolationsByGlobal(Map<String,Object> params);

    //根据筛选条件获取元数据
    List<ResponseServiceScoreDto> getServiceTotalScoreDto(RequestServiceScoreDto requestServiceScoreDto);

    //根据taskType获取违规统计
    Integer getProcessScoreByType(Map<String,Object> params);

    Integer  getGlobalScoreByType(Map<String,Object> params);

    Integer getTotalServiceScore(RequestServiceScoreDto requestServiceScoreDto);

    /**
      * @Description:  获取历史中客服
    　* @author kloazhang
    　* @date 2018/9/21 17:54
    */
    List<ResponseServiceDto> getAllService(@Param(value = "dbName") String dbName);

    /**
      * @Description:  获取分数统计查询元数据中得taskId
    　* @author kloazhang
    　* @date 2018/9/21 18:45
    */
    List<TotalTaskInfoDto> getTaskIdAndService(RequestServiceScoreDto requestServiceScoreDto);

    int deleteTaskLogBySessionId(@Param(value = "sessionId") String sessionId,@Param(value = "dbName") String dbName);

    /**
      * @Description: 训练历史流水批量入库
    　* @param ${tags}
    　* @return ${return_type}
    　* @throws
    　* @author kloazhang
    　* @date 2019/1/23 16:24
    */
    int batchInsertChatLog(Map<String,Object> params);

    /**
      * @Description: 训练历史触发得分入库
    　* @param ${tags}
    　* @return ${return_type}
    　* @throws
    　* @author ${USER}
    　* @date 2019/1/23 16:25
    */
    int batchInsertTaskScore(Map<String,Object> params);

    /**
      * @Description: 获取业务库下对应的task总数
    　* @param ${tags}
    　* @return ${return_type}
    　* @throws
    　* @author ${USER}
    　* @date 2019/7/9 14:38
    */
    int countTaskInfoHistory(@Param("dbName") String dbName);

}
