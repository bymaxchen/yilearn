package com.zhuiyi.ms.learn.controller;


import com.zhuiyi.ms.learn.api.RuleInfoIF;
import com.zhuiyi.ms.learn.dto.request.RequestRuleInfoDto;
import com.zhuiyi.ms.learn.dto.request.RequestSemanticToRuleDto;
import com.zhuiyi.ms.learn.dto.response.ResponseReviewRulesDto;
import com.zhuiyi.ms.learn.dto.response.BaseResponse;
import com.zhuiyi.ms.learn.dto.response.ResponseVO;
import com.zhuiyi.ms.learn.enums.MsgCodeEnum;
import com.zhuiyi.ms.learn.service.RuleInfoServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author rodbate
 * @Title: RuleInfoController
 * @ProjectName learn
 * @Description: TODO
 * @date 2018/9/1817:39
 */
@RestController
public class RuleInfoController implements RuleInfoIF {

    private static final Logger LOGGER = LoggerFactory.getLogger(RuleInfoController.class);

    private static final Integer EXIST_CODE = -505;

    @Autowired
    private RuleInfoServiceImpl ruleInfoService;

    @Override
    public BaseResponse saveRuleInfo(@RequestBody RequestRuleInfoDto requestRuleInfoDto){

        BaseResponse baseResponse;
        try{
            baseResponse = new BaseResponse(MsgCodeEnum.SUCCESS);
            int result = ruleInfoService.saveRuleInfo(requestRuleInfoDto);
            if(result == EXIST_CODE){
                baseResponse.setMsg("RuleInfo Name Exists !!!");
                baseResponse.setCode(EXIST_CODE);
            }
            baseResponse.setData(requestRuleInfoDto);
        }catch(Exception ex){
            LOGGER.info(ex.toString());
            baseResponse = new BaseResponse();
            baseResponse.setData(ex.toString());
            baseResponse.setCode(500);
        }
        return baseResponse;
    }

    @Override
    public BaseResponse updateRuleInfo(@RequestBody RequestRuleInfoDto requestRuleInfoDto){

        BaseResponse baseResponse;
        try{
            baseResponse = new BaseResponse(MsgCodeEnum.SUCCESS);
            int result = ruleInfoService.updateRuleInfo(requestRuleInfoDto);
            if(result == EXIST_CODE){
                baseResponse.setMsg("RuleInfo Name Exists !!!");
                baseResponse.setCode(EXIST_CODE);
            }
            baseResponse.setData(result);
        }catch(Exception ex){
            LOGGER.info(ex.toString());
            baseResponse = new BaseResponse();
            baseResponse.setData(ex.toString());
            baseResponse.setCode(500);
        }
        return baseResponse;
    }

    @Override
    public BaseResponse deleteRuleInfo(@RequestBody RequestRuleInfoDto requestRuleInfoDto){

        BaseResponse baseResponse = new BaseResponse(MsgCodeEnum.SUCCESS);
        int id = requestRuleInfoDto.getId();
        baseResponse.setData(ruleInfoService.deleteRuleInfo(id));
        return baseResponse;
    }

    @Override
    public BaseResponse<ResponseVO> getRules(@RequestBody RequestRuleInfoDto requestRuleInfoDto){

        BaseResponse baseResponse = new BaseResponse(MsgCodeEnum.SUCCESS);
        ResponseVO responseVO = new ResponseVO();
        responseVO = ruleInfoService.getRules(requestRuleInfoDto);
        baseResponse.setData(responseVO);
        return baseResponse;
    }

    @Override
    public BaseResponse<ResponseVO> getInvolvedRulesBySemanticId(@RequestBody RequestSemanticToRuleDto requestSemanticToRuleDto) {

        BaseResponse baseResponse = new BaseResponse(MsgCodeEnum.SUCCESS);
        ResponseVO responseVO = new ResponseVO();
        responseVO =ruleInfoService.getInvolvedRulesBySemanticId(requestSemanticToRuleDto);
        baseResponse.setData(responseVO);
        return baseResponse;
    }

    /**
      * @Description: 获取该业务库下的所有规则
    　* @param ${tags} 
    　* @return ${return_type} 
    　* @throws
    　* @author ${USER}
    　* @date 2019/1/16 16:24
    */
    @Override
    public BaseResponse<ResponseReviewRulesDto> getReviewRules(){

        BaseResponse baseResponse = new BaseResponse(MsgCodeEnum.SUCCESS);
        ResponseReviewRulesDto responseReviewRulesDto = new ResponseReviewRulesDto();
        responseReviewRulesDto =ruleInfoService.getReviewRules();
        baseResponse.setData(responseReviewRulesDto);
        return baseResponse;
    }

}
