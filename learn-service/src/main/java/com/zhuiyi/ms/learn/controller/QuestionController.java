package com.zhuiyi.ms.learn.controller;

import com.zhuiyi.ms.learn.api.QuestionIF;
import com.zhuiyi.ms.learn.dto.request.AddQuestionClassifyRequest;
import com.zhuiyi.ms.learn.dto.request.AddQuestionNodeRequest;
import com.zhuiyi.ms.learn.dto.request.AddQuestionRequest;
import com.zhuiyi.ms.learn.dto.request.BaseRequest;
import com.zhuiyi.ms.learn.dto.request.DeleteQuestionClassifyRequest;
import com.zhuiyi.ms.learn.dto.request.DeleteQuestionNodeRequest;
import com.zhuiyi.ms.learn.dto.request.DeleteQuestionRequest;
import com.zhuiyi.ms.learn.dto.request.QueryQuestionListRequest;
import com.zhuiyi.ms.learn.dto.request.QueryQuestionNodeRequest;
import com.zhuiyi.ms.learn.dto.request.QueryQuestionRequest;
import com.zhuiyi.ms.learn.dto.request.UpdateQuestionClassifyRequest;
import com.zhuiyi.ms.learn.dto.request.UpdateQuestionNodeRequest;
import com.zhuiyi.ms.learn.dto.request.UpdateQuestionRequest;
import com.zhuiyi.ms.learn.dto.response.BaseResponse;
import com.zhuiyi.ms.learn.dto.response.QueryQuestionListResponse;
import com.zhuiyi.ms.learn.dto.response.QuestionClassifyListResponse;
import com.zhuiyi.ms.learn.dto.transfer.QuestionInfoDto;
import com.zhuiyi.ms.learn.dto.transfer.QuestionNodeDto;
import com.zhuiyi.ms.learn.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 此类的描述是：
 *
 * @author crazyhu@wezhuiyi.com
 * @date 2018-09-18 14:20
 **/
@RestController
public class QuestionController implements QuestionIF {

    @Autowired
    private QuestionService questionService;

    @Override
    public BaseResponse<Long> addQuestionClassify(@Valid @RequestBody AddQuestionClassifyRequest request) {
        return questionService.addQuestionClassify(request);
    }

    @Override
    public BaseResponse<String> updateQuestionClassify(@Valid @RequestBody UpdateQuestionClassifyRequest request) {
        return questionService.updateQuestionClassify(request);
    }

    @Override
    public BaseResponse<QuestionClassifyListResponse> questionClassifyList(@Valid @RequestBody BaseRequest request) {
        return questionService.questionClassifyList(request);
    }

    @Override
    public BaseResponse<String> deleteQuestionClassify(@Valid @RequestBody DeleteQuestionClassifyRequest request) {
        return questionService.deleteQuestionClassify(request);
    }

    @Override
    public BaseResponse<Long> addQuestion(@Valid @RequestBody AddQuestionRequest request) {
        return questionService.addQuestion(request);
    }

    @Override
    public BaseResponse<String> updateQuestion(@Valid @RequestBody UpdateQuestionRequest request) {
        return questionService.updateQuestion(request);
    }

    @Override
    public BaseResponse<QueryQuestionListResponse> queryQuestionList(@Valid @RequestBody QueryQuestionListRequest request) {
        return questionService.queryQuestionList(request);
    }

    @Override
    public BaseResponse<QuestionInfoDto> queryQuestion(@Valid @RequestBody QueryQuestionRequest request) {
        return questionService.queryQuestion(request);
    }

    @Override
    public BaseResponse<String> deleteQuestion(@Valid @RequestBody DeleteQuestionRequest request) {
        return questionService.deleteQuestion(request);
    }

    @Override
    public BaseResponse<Long> addQuestionNode(@Valid @RequestBody AddQuestionNodeRequest request) {
        return questionService.addQuestionNode(request);
    }

    @Override
    public BaseResponse<Long> updateQuestionNode(@Valid @RequestBody UpdateQuestionNodeRequest request) {
        return questionService.updateQuestionNode(request);
    }

    @Override
    public BaseResponse<QuestionNodeDto> queryQuestionNode(@Valid @RequestBody QueryQuestionNodeRequest request) {
        return questionService.queryQuestionNode(request);
    }

    @Override
    public BaseResponse<Long> deleteQuestionNode(@Valid @RequestBody DeleteQuestionNodeRequest request) {
        return questionService.deleteQuestionNode(request);
    }

}
