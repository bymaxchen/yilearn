package com.zhuiyi.ms.learn.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * @author rodbate
 * @Title: DataBaseInfoMapper
 * @ProjectName learn
 * @Description: TODO
 * @date 2018/11/616:15
 */
@Mapper
public interface DataBaseInfoMapper {

   int closeForeignKeyCheck();

   int openForeignKeyCheck();
   /**
    *  根据busId返回对应的业务库名
    * @param busId
    * @return
    */
   String getDbNameByBusId(@Param(value = "busId") String busId);

   /**
    *  创建数据库及表
    * @param  dbName
    * @return
    */
   int createAudio(Map<String,Object> params);

   int createDataBusiness(Map<String,Object> params);

   int createChatLog(Map<String,Object> params);

   int createCategory(Map<String,Object> params);

   int createBaseQuestion(Map<String,Object> params);

   int createChallengeExam(Map<String,Object> params);

   int createChallengeMission(Map<String,Object> params);

   int createExamSession(Map<String,Object> params);

   int createExamTraining(Map<String,Object> params);

   int createRules(Map<String,Object> params);

   int createTaskInfoHistory(Map<String,Object> params);

   int createTaskScoreHistory(Map<String,Object> params);

   int createEasyQuestion(Map<String,Object> params);

   int createExam(Map<String,Object> params);

   int createExamPart(Map<String,Object> params);

   int createExamPartQuestion(Map<String,Object> params);

   int createPaper(Map<String,Object> params);

   int createPaperCategory(Map<String,Object> params);

   int createPaperPart(Map<String,Object> params);

   int createPaperPartQuestion(Map<String,Object> params);

   int createQuestionCategory(Map<String,Object> params);

   int createQuestionRule(Map<String,Object> params);

   int createQuestionRuleCategory(Map<String,Object> params);

   int createSceneQuestion(Map<String,Object> params);

   int createSceneRule(Map<String,Object> params);

   int createSceneRuleCategory(Map<String,Object> params);

   int createScoreDetail(Map<String,Object> params);

   int createDefaultGlobalRule(Map<String,Object> params);

   int createDefaultPaperCategory(Map<String,Object> params);

   int createDefaultQuestionCategory(Map<String,Object> params);

   int createDefaultQuestionRuleCategory(Map<String,Object> params);

   int createDefaultSceneRuleCategory(Map<String,Object> params);

   int insertDataBusiness(Map<String,Object> params);

   int deleteBusinessDb(Map<String,Object> params);

   int createDefaultRule(Map<String,Object> params);

   int createDefaultCategory(Map<String,Object> params);
}
