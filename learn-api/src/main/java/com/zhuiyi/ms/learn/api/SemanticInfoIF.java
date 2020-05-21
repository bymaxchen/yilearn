package com.zhuiyi.ms.learn.api;

import com.zhuiyi.ms.learn.dto.request.RequestSemanticDto;
import com.zhuiyi.ms.learn.dto.request.RequestSemanticToRuleDto;
import com.zhuiyi.ms.learn.dto.response.BaseResponse;
import com.zhuiyi.ms.learn.dto.response.ResponseVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author rodbate
 * @Title: SemanticInfoIF
 * @ProjectName learn
 * @Description: TODO
 * @date 2018/9/1913:09
 */
@RequestMapping("/learn/semantic")
public interface SemanticInfoIF {

    @PostMapping("/createSemantic")
    BaseResponse<ResponseVO> createSemantic(RequestSemanticDto requestSemanticDto);

    @PostMapping("/updateSemantic")
    BaseResponse<ResponseVO> updateSemantic(RequestSemanticDto requestSemanticDto);

    @PostMapping("/deleteSemantic")
    BaseResponse<ResponseVO> deleteSemantic(RequestSemanticDto requestSemanticDto);

    @PostMapping("/getSemantic")
    BaseResponse<ResponseVO> getSemantic(RequestSemanticDto requestSemanticDto);

    @PostMapping("/getRulesBySemanticId")
    BaseResponse<ResponseVO> getRulesBySemanticId(RequestSemanticToRuleDto requestSemanticToRuleDto);
}
