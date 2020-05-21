package com.zhuiyi.ms.learn.controller;

import com.zhuiyi.ms.learn.api.SemanticInfoIF;
import com.zhuiyi.ms.learn.dto.request.RequestSemanticToRuleDto;
import com.zhuiyi.ms.learn.dto.response.ResponseVO;
import com.zhuiyi.ms.learn.enums.MsgCodeEnum;
import com.zhuiyi.ms.learn.dto.request.RequestSemanticDto;
import com.zhuiyi.ms.learn.dto.response.BaseResponse;
import com.zhuiyi.ms.learn.service.SemanticInfoServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author rodbate
 * @Title: SemanticInfoController
 * @ProjectName learn
 * @Description: TODO
 * @date 2018/9/1817:05
 */
@RequestMapping("/learn/semantic")
@RestController
public class SemanticInfoController implements SemanticInfoIF {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryInfoController.class);

    @Autowired
    private SemanticInfoServiceImpl semanticInfoService;

    @Override
    public BaseResponse createSemantic(@RequestBody RequestSemanticDto requestSemanticDto){

        BaseResponse baseResponse;
        try{
            baseResponse = new BaseResponse(MsgCodeEnum.SUCCESS);
            int result = 0;
            result = semanticInfoService.createSemantic(requestSemanticDto);
            baseResponse.setData(requestSemanticDto);
        }catch(Exception ex){
            LOGGER.info(ex.toString());
            baseResponse = new BaseResponse();
            baseResponse.setData(ex.toString());
            baseResponse.setCode(500);
        }
        return baseResponse;
    }

    @Override
    public BaseResponse updateSemantic(@RequestBody RequestSemanticDto requestSemanticDto){

        BaseResponse baseResponse;
        try{
            baseResponse = new BaseResponse(MsgCodeEnum.SUCCESS);
            baseResponse.setData(semanticInfoService.updateSemantic(requestSemanticDto));
        }catch(Exception ex){
            LOGGER.info(ex.toString());
            baseResponse = new BaseResponse();
            baseResponse.setData(ex.toString());
            baseResponse.setCode(500);
        }
        return baseResponse;
    }

    @Override
    public BaseResponse deleteSemantic(@RequestBody RequestSemanticDto requestSemanticDto){

        BaseResponse baseResponse = new BaseResponse(MsgCodeEnum.SUCCESS);
        int semanticId = requestSemanticDto.getId();
        baseResponse.setData(semanticInfoService.deleteSemantic(semanticId));
        return baseResponse;
    }

    @Override
    public BaseResponse<ResponseVO> getSemantic(@RequestBody RequestSemanticDto requestSemanticDto){

        BaseResponse baseResponse = new BaseResponse(MsgCodeEnum.SUCCESS);
        ResponseVO responseVO = new ResponseVO();
        responseVO =semanticInfoService.getSemantic(requestSemanticDto);
        baseResponse.setData(responseVO);
        return baseResponse;
    }

    @Override
    public BaseResponse<ResponseVO> getRulesBySemanticId(@RequestBody RequestSemanticToRuleDto requestSemanticToRuleDto) {

        BaseResponse baseResponse = new BaseResponse(MsgCodeEnum.SUCCESS);
        ResponseVO responseVO = new ResponseVO();
        responseVO =semanticInfoService.getRulesBySemanticId(requestSemanticToRuleDto);
        baseResponse.setData(responseVO);
        return baseResponse;
    }

}
