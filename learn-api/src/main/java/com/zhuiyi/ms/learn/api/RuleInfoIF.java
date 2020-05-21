package com.zhuiyi.ms.learn.api;

import com.alibaba.fastjson.JSONObject;
import com.zhuiyi.ms.learn.dto.request.RequestRuleInfoDto;
import com.zhuiyi.ms.learn.dto.request.RequestSemanticToRuleDto;
import com.zhuiyi.ms.learn.dto.response.BaseResponse;
import com.zhuiyi.ms.learn.dto.response.ResponseReviewRulesDto;
import com.zhuiyi.ms.learn.dto.response.ResponseVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author rodbate
 * @Title: RuleInfoIF
 * @ProjectName learn
 * @Description: TODO
 * @date 2018/9/1912:59
 */
@RequestMapping("/learn/rule")
public interface RuleInfoIF {

    @PostMapping("/saveRuleInfo")
    BaseResponse<ResponseVO> saveRuleInfo(RequestRuleInfoDto requestRuleInfoDto);

    @PostMapping("/updateRuleInfo")
    BaseResponse<ResponseVO> updateRuleInfo(RequestRuleInfoDto requestRuleInfoDto);

    @PostMapping("/deleteRuleInfo")
    BaseResponse<ResponseVO> deleteRuleInfo(RequestRuleInfoDto requestRuleInfoDto);

    @PostMapping("/getRules")
    BaseResponse<ResponseVO> getRules(RequestRuleInfoDto requestRuleInfoDto);

    @PostMapping("/getInvolvedRulesBySemanticId")
    BaseResponse<ResponseVO> getInvolvedRulesBySemanticId(RequestSemanticToRuleDto  requestSemanticToRuleDto);
    
    /**
      * @Description: 获取该业务库下的所有规则
    　* @param
    　* @return 返回该业务库下所有规则
    　* @throws
    　* @author kloazhang
    　* @date 2019/1/16 16:18
    */
    @PostMapping("/getReviewRules")
    BaseResponse<ResponseReviewRulesDto> getReviewRules();

}
