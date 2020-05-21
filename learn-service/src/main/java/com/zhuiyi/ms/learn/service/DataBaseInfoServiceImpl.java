package com.zhuiyi.ms.learn.service;

import com.zhuiyi.ms.learn.common.DateUtil;
import com.zhuiyi.ms.learn.dto.request.CreateBusinessDTO;
import com.zhuiyi.ms.learn.dto.response.ResponseVO;
import com.zhuiyi.ms.learn.mapper.DataBaseInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * @author rodbate
 * @Title: DataBaseInfoServiceImpl
 * @ProjectName learn
 * @Description: TODO
 * @date 2018/11/616:21
 */
@Service("dataBaseInfoService")
public class DataBaseInfoServiceImpl {

//    @Value("${taiBaoRemote}")
//    private Boolean taiBaoRemote;

    @Autowired
    private DataBaseInfoMapper dataBaseInfoMapper;
    public String getDbNameByBusId(String busId){

        //1.查询现有的库，若有则返回，若无则创建库及表
        String dbName = dataBaseInfoMapper.getDbNameByBusId(busId);
        return  dbName;
    }

    @Transactional(rollbackFor = Exception.class)
    public String createBusinessDb(CreateBusinessDTO createBusinessDTO){
        String existBusiness =  dataBaseInfoMapper.getDbNameByBusId(createBusinessDTO.getBusId());

        if(existBusiness == null) {
            String pre = "ds_learn_";
            String dbName = pre.concat(createBusinessDTO.getBusId());
            //1.建库/表
            Map<String, Object> params = new HashMap<>();
            params.put("dbName", dbName);
            dataBaseInfoMapper.createDataBusiness(params);

            dataBaseInfoMapper.closeForeignKeyCheck();

            dataBaseInfoMapper.createCategory(params);
            dataBaseInfoMapper.createRules(params);
            dataBaseInfoMapper.createTaskInfoHistory(params);
            dataBaseInfoMapper.createTaskScoreHistory(params);

            dataBaseInfoMapper.createAudio(params);
            dataBaseInfoMapper.createQuestionCategory(params);
            dataBaseInfoMapper.createBaseQuestion(params);
            dataBaseInfoMapper.createChallengeExam(params);
            dataBaseInfoMapper.createChallengeMission(params);

            dataBaseInfoMapper.createExamSession(params);
            dataBaseInfoMapper.createExamTraining(params);

            dataBaseInfoMapper.createEasyQuestion(params);
            dataBaseInfoMapper.createExam(params);
            dataBaseInfoMapper.createExamPart(params);
            dataBaseInfoMapper.createExamPartQuestion(params);
            dataBaseInfoMapper.createPaper(params);
            dataBaseInfoMapper.createPaperCategory(params);
            dataBaseInfoMapper.createPaperPart(params);
            dataBaseInfoMapper.createPaperPartQuestion(params);


            dataBaseInfoMapper.createQuestionRuleCategory(params);
            dataBaseInfoMapper.createQuestionRule(params);
            dataBaseInfoMapper.createSceneQuestion(params);

            dataBaseInfoMapper.createSceneRuleCategory(params);
            dataBaseInfoMapper.createSceneRule(params);
            dataBaseInfoMapper.createScoreDetail(params);
            dataBaseInfoMapper.createChatLog(params);





            //初始化默认规则
            //更新现有得业务库
            params.put("busId", createBusinessDTO.getBusId());
            params.put("company", createBusinessDTO.getCompany());
            params.put("startTime", new Timestamp(System.currentTimeMillis()));
            dataBaseInfoMapper.insertDataBusiness(params);
            params.clear();
            params.put("dbName", dbName);
            params.put("createTime", DateUtil.TimestampToString(new Timestamp(System.currentTimeMillis()),
                    "yyyy-MM-dd HH:mm:ss"));
            params.put("updateTime", DateUtil.TimestampToString(new Timestamp(System.currentTimeMillis()),
                    "yyyy-MM-dd HH:mm:ss"));


            params.put("wordStatus", 0);
            dataBaseInfoMapper.createDefaultRule(params);
            dataBaseInfoMapper.createDefaultCategory(params);
            dataBaseInfoMapper.createDefaultGlobalRule(params);
            dataBaseInfoMapper.createDefaultPaperCategory(params);
            dataBaseInfoMapper.createDefaultQuestionCategory(params);
            dataBaseInfoMapper.createDefaultQuestionRuleCategory(params);
            dataBaseInfoMapper.createDefaultSceneRuleCategory(params);

            dataBaseInfoMapper.openForeignKeyCheck();

            return "create business success !!!";
        }
        String message = "business exist !!!";
        return message;
    }

    public int deleteBusinessDb(CreateBusinessDTO createBusinessDTO){

        Map<String,Object> params = new HashMap<>();
        params.put("busId", createBusinessDTO.getBusId());
        return dataBaseInfoMapper.deleteBusinessDb(params);
    }
}

