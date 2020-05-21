package com.zhuiyi.ms.learn.api;

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
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 此类的描述是：
 *
 * @author crazyhu@wezhuiyi.com
 * @date 2018-09-18 14:16
 **/
public interface QuestionIF {

    @PostMapping("/questionClassify/add")
    BaseResponse<Long> addQuestionClassify(AddQuestionClassifyRequest request);

    @PostMapping("/questionClassify/update")
    BaseResponse<String> updateQuestionClassify(UpdateQuestionClassifyRequest request);

    @PostMapping("/questionClassify/list")
    BaseResponse<QuestionClassifyListResponse> questionClassifyList(BaseRequest request);

    @PostMapping("/questionClassify/delete")
    BaseResponse<String> deleteQuestionClassify(DeleteQuestionClassifyRequest request);

    @PostMapping("/question/add")
    BaseResponse<Long> addQuestion(AddQuestionRequest request);

    @PostMapping("/question/update")
    BaseResponse<String> updateQuestion(UpdateQuestionRequest request);

    @PostMapping("/question/list")
    BaseResponse<QueryQuestionListResponse> queryQuestionList(QueryQuestionListRequest request);

    @PostMapping("/question/detail")
    BaseResponse<QuestionInfoDto> queryQuestion(QueryQuestionRequest request);

    @PostMapping("/question/delete")
    BaseResponse<String> deleteQuestion(DeleteQuestionRequest request);

    @PostMapping("/questionNode/add")
    BaseResponse<Long> addQuestionNode(AddQuestionNodeRequest request);

    @PostMapping("/questionNode/update")
    BaseResponse<Long> updateQuestionNode(UpdateQuestionNodeRequest request);

    @PostMapping("/questionNode/detail")
    BaseResponse<QuestionNodeDto> queryQuestionNode(QueryQuestionNodeRequest request);

    @PostMapping("/questionNode/delete")
    BaseResponse<Long> deleteQuestionNode(DeleteQuestionNodeRequest request);
}
