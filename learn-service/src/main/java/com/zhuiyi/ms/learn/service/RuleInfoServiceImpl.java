package com.zhuiyi.ms.learn.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.zhuiyi.ms.learn.common.DateUtil;
import com.zhuiyi.ms.learn.common.enums.RuleEnum;
import com.zhuiyi.ms.learn.dto.SessionContextHolder;
import com.zhuiyi.ms.learn.dto.request.*;
import com.zhuiyi.ms.learn.dto.response.*;
import com.zhuiyi.ms.learn.entity.RuleInfoEntity;
import com.zhuiyi.ms.learn.mapper.RuleInfoMapper;
import com.zhuiyi.ms.learn.mapper.SemanticInfoMapper;
import com.zhuiyi.ms.learn.mapper.TQuestionRuleMapper;
import com.zhuiyi.ms.learn.mapper.TSceneRuleMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;

/**
 * @author rodbate
 * @Title: RuleInfoServiceImpl
 * @ProjectName learn
 * @Description: TODO
 * @date 2018/9/1817:23
 */
@Service
public class RuleInfoServiceImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(RuleInfoServiceImpl.class);

    @Autowired
    private RuleInfoMapper ruleInfoMapper;

    @Autowired
    private SemanticInfoMapper semanticInfoMapper;

    @Autowired
    private TSceneRuleMapper tSceneRuleMapper;

    @Autowired
    private TQuestionRuleMapper tQuestionRuleMapper;

    /**
      * @Description:  保存规则
    　* @author kloazhang
    　* @date 2018/9/18 17:34 
    */
    public int saveRuleInfo(RequestRuleInfoDto requestRuleInfoDto) throws Exception{

        //1.t_rule_info入库
        String dbName = SessionContextHolder.getDbName();
        Date createTime = DateUtil.timestamp2Date(System.currentTimeMillis(),"yyyy-MM-dd HH:mm:ss");
        requestRuleInfoDto.setCreateTime(createTime);
        requestRuleInfoDto.setUpdateTime(createTime);
        //1.判断规则名称是否重复，若重复返回55
        Map<String,Object> countParams = new HashMap<>();
        countParams.put("dbName", dbName);
        countParams.put("ruleName", requestRuleInfoDto.getName());
        Integer existNums = ruleInfoMapper.countExistRuleName(countParams);
        if(null != existNums && existNums >0){
            return -505;
        }
        if(null != requestRuleInfoDto.getDesc()){
            requestRuleInfoDto.setDescription(requestRuleInfoDto.getDesc());
        }
        requestRuleInfoDto.setDbName(dbName);
        ruleInfoMapper.saveRuleInfo(requestRuleInfoDto);
        //2.遍历触发得标签semanticIds:
        List<Integer> semanticIds = new ArrayList<>();
        List<RuleConditionDto> ruleConditionDtos = new ArrayList<>();
        if(null != requestRuleInfoDto.getConditions()){
            ruleConditionDtos =  JSONObject.parseArray(requestRuleInfoDto.getConditions(),RuleConditionDto.class);
            //获取此次规则关联的所有语义标签
            for(RuleConditionDto ruleConditionDto:ruleConditionDtos){
                if(null != ruleConditionDto.getFactors() && ruleConditionDto.getFactors().size()>0){
                    for(FactorDto factorDto:ruleConditionDto.getFactors()){
                        if(factorDto.getCheckType() ==1){
                            semanticIds.add(Integer.valueOf(factorDto.getCheckValue()));
                        }
                    }
                }
            }
        }
        //返回插入得规则Id
        return requestRuleInfoDto.getId();
    }

    /**
      * @Description: 更新规则
    　* @author kloazhang
    　* @date 2018/9/18 17:34 
    */
    public int updateRuleInfo(RequestRuleInfoDto requestRuleInfoDto) throws Exception{

        String dbName = SessionContextHolder.getDbName();
        int result =0;
        //1.判断规则名称是否重复，若重复返回505
        if(StringUtils.isNotEmpty(requestRuleInfoDto.getName())){
            Map<String,Object> countParams = new HashMap<>();
            countParams.put("ruleName", requestRuleInfoDto.getName());
            countParams.put("ruleId", requestRuleInfoDto.getId());
            countParams.put("dbName", dbName);
            int existNums = ruleInfoMapper.countExistRuleName(countParams);
            if(existNums > 0){
                return -505;
            }
        }
        Date updateTime = DateUtil.timestamp2Date(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss");
        requestRuleInfoDto.setUpdateTime(updateTime);
        if(null != requestRuleInfoDto.getDesc()){
            requestRuleInfoDto.setDescription(requestRuleInfoDto.getDesc());
        }
        //1.修改rules表
        requestRuleInfoDto.setDbName(dbName);
        result = ruleInfoMapper.updateRuleInfo(requestRuleInfoDto);
        return result;
    }

    /**
      * @Description: 删除规则
    　* @author kloazhang
    　* @date 2018/9/18 17:37
    */
    public int deleteRuleInfo(int ruleId) {

        //1.删除rules表
        int result = 0;
        String dbName = SessionContextHolder.getDbName();
        result = ruleInfoMapper.deleteRuleInfo(ruleId,dbName);
        return result;
    }

    public ResponseVO getRules(RequestRuleInfoDto requestRuleInfoDto) {

        String dbName = SessionContextHolder.getDbName();
        Map<String,Object> params = new HashMap<>();
        ResponseVO responseVO = new ResponseVO();
        List<ResponseRuleInfoDto> responseRuleInfoDtos = new ArrayList<>();
        params.put("ruleId", requestRuleInfoDto.getId());
        params.put("ruleIds",requestRuleInfoDto.getRuleIds());
        params.put("ruleType", requestRuleInfoDto.getRuleType());
        params.put("dbName", dbName);
        if(null != requestRuleInfoDto.getPagination() &&
                true == requestRuleInfoDto.getPagination()){
            int currentPage = 0 ;
            if(requestRuleInfoDto.getCurrentPage() > 0){
                currentPage = requestRuleInfoDto.getCurrentPage() - 1;
            }
            int pageSize = requestRuleInfoDto.getPageSize();
            if(pageSize <= 0 ){
                pageSize = 10;
            }
            params.put("limit",pageSize);
            params.put("offset",currentPage * pageSize);
            int totalNums = ruleInfoMapper.countRules(params);
            int allPages = (totalNums + pageSize - 1) / pageSize;
            responseVO.setTotal(totalNums );
            responseVO.setAllPages(allPages);
            responseVO.setPageSize(pageSize == 0? 1:pageSize);
            responseVO.setCurrentPage(requestRuleInfoDto.getCurrentPage()==0?1:requestRuleInfoDto.getCurrentPage());
            if(totalNums>0){
                responseRuleInfoDtos=ruleInfoMapper.getRules(params);
            }
            if(responseRuleInfoDtos.size()>0){
                for(ResponseRuleInfoDto responseRuleInfoDto: responseRuleInfoDtos){
                    if(null != responseRuleInfoDto.getCreateTimeDate()){
                        String createTime = DateUtil.date2String(responseRuleInfoDto.getCreateTimeDate(),
                                "yyyy-MM-dd HH:mm:ss");
                        responseRuleInfoDto.setCreateTime(createTime);
                    }
                     if(null!= responseRuleInfoDto.getUpdateTimeDate()){
                         String updateTime = DateUtil.date2String(responseRuleInfoDto.getUpdateTimeDate(),
                                 "yyyy-MM-dd HH:mm:ss");
                         responseRuleInfoDto.setUpdateTime(updateTime);
                     }
                     responseRuleInfoDto.setDesc(responseRuleInfoDto.getDescription());
                }
            }
            responseVO.setData(responseRuleInfoDtos);
            return  responseVO;
        }
        responseRuleInfoDtos=ruleInfoMapper.getRules(params);
        if(responseRuleInfoDtos.size()>0){
            for(ResponseRuleInfoDto responseRuleInfoDto: responseRuleInfoDtos){
                if(null != responseRuleInfoDto.getCreateTimeDate()){
                    String createTime = DateUtil.date2String(responseRuleInfoDto.getCreateTimeDate(),
                            "yyyy-MM-dd HH:mm:ss");
                    responseRuleInfoDto.setCreateTime(createTime);
                }
                if(null!= responseRuleInfoDto.getUpdateTimeDate()){
                    String updateTime = DateUtil.date2String(responseRuleInfoDto.getUpdateTimeDate(),
                            "yyyy-MM-dd HH:mm:ss");
                    responseRuleInfoDto.setUpdateTime(updateTime);
                }
                responseRuleInfoDto.setDesc(responseRuleInfoDto.getDescription());
            }
        }
        responseVO.setData(responseRuleInfoDtos);
        responseVO.setTotal(responseRuleInfoDtos.size());
        return  responseVO;
    }

    @DS("#session.dataSourceName")
    public ResponseVO getInvolvedRulesBySemanticId (RequestSemanticToRuleDto requestSemanticToRuleDto){

        ResponseVO responseVO = new ResponseVO();
        Map<String,Object> params = new HashMap<>();
        String dbName = SessionContextHolder.getDbName();
        List<SemanticInvolvedRulesDto> semanticInvolvedRulesDtos = new ArrayList<>();
        if(!requestSemanticToRuleDto.getSemanticIds().isEmpty()){
           for(long semanticId : requestSemanticToRuleDto.getSemanticIds()){

               List<InvolvedRulesDto> involvedSceneRulesList = tSceneRuleMapper.getInvolvedRulesBySemanticId(semanticId);
               involvedSceneRulesList.forEach(item -> item.setExamRuleType(RuleEnum.SCENE_RULE_TYPE.value()));

               List<InvolvedRulesDto> involvedQuestionRulesList = tQuestionRuleMapper.getInvolvedRulesBySemanticId(semanticId);
               involvedQuestionRulesList.forEach(item -> item.setExamRuleType(RuleEnum.QUESTION_RULE_TYPE.value()));

               involvedSceneRulesList.addAll(involvedQuestionRulesList);

               SemanticInvolvedRulesDto semanticInvolvedRulesDto = new SemanticInvolvedRulesDto();
               semanticInvolvedRulesDto.setSemanticId(semanticId);
               semanticInvolvedRulesDto.setInvolvedRules(involvedSceneRulesList);
               semanticInvolvedRulesDto.setInvolvedRuleNums(involvedSceneRulesList.size() );
               semanticInvolvedRulesDtos.add(semanticInvolvedRulesDto);
           }
        }
        responseVO.setData(semanticInvolvedRulesDtos);
        return  responseVO;
    }


    /**
      * @Description: ${todo}
    　* @param ${tags}
    　* @return ${return_type}
    　* @throws
    　* @author ${USER}
    　* @date 2019/1/16 16:34
    */
    public ResponseReviewRulesDto getReviewRules(){

        ResponseReviewRulesDto responseReviewRulesDto = new ResponseReviewRulesDto();
        Map<String,Object> params = new HashMap<>();
        params.put("dbName", SessionContextHolder.getDbName());
        List<RuleInfoEntity> ruleInfoEntities = new ArrayList<>();
        ruleInfoEntities = ruleInfoMapper.getReviewRules(params);
        if(ruleInfoEntities.size() > 0){
            List<ReviewRulesDto> reviewRulesDtos = new ArrayList<>();
            for(RuleInfoEntity ruleInfoEntity : ruleInfoEntities){
                ReviewRulesDto reviewRulesDto = new ReviewRulesDto();
                reviewRulesDto.setId(ruleInfoEntity.getId());
                reviewRulesDto.setName(ruleInfoEntity.getName());
                reviewRulesDto.setCategoryId(ruleInfoEntity.getCategoryId());
                reviewRulesDto.setRelation(ruleInfoEntity.getRelation());
                reviewRulesDto.setScore(ruleInfoEntity.getScore());
                reviewRulesDto.setDescription(ruleInfoEntity.getDescription());
                reviewRulesDto.setConditions(JSON.parseArray(ruleInfoEntity.getConditions()));
                reviewRulesDto.setRawConditions(JSON.parseArray(ruleInfoEntity.getRawConditions()));
                reviewRulesDto.setUseType(ruleInfoEntity.getUseType());
                reviewRulesDto.setRuleType(ruleInfoEntity.getRuleType());
                reviewRulesDtos.add(reviewRulesDto);
            }
            responseReviewRulesDto.setTotalRules(reviewRulesDtos.size());
            responseReviewRulesDto.setAllRules(reviewRulesDtos);
            return responseReviewRulesDto;
        }
        responseReviewRulesDto.setTotalRules(0);
        return responseReviewRulesDto;

    }

}
