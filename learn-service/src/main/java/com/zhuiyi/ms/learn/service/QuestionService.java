package com.zhuiyi.ms.learn.service;

import com.zhuiyi.ms.learn.common.Page;
import com.zhuiyi.ms.learn.common.enums.DeleteStatus;
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
import com.zhuiyi.ms.learn.dto.transfer.QuestionClassifyDto;
import com.zhuiyi.ms.learn.dto.transfer.QuestionInfoDto;
import com.zhuiyi.ms.learn.dto.transfer.QuestionNodeDto;
import com.zhuiyi.ms.learn.entity.QuestionClassify;
import com.zhuiyi.ms.learn.entity.QuestionInfo;
import com.zhuiyi.ms.learn.entity.QuestionNode;
import com.zhuiyi.ms.learn.entity.QuestionRule;
import com.zhuiyi.ms.learn.entity.filter.QuestionFilter;
import com.zhuiyi.ms.learn.entity.vo.QuestionClassifyVo;
import com.zhuiyi.ms.learn.mapper.QuestionClassifyMapper;
import com.zhuiyi.ms.learn.mapper.QuestionInfoMapper;
import com.zhuiyi.ms.learn.mapper.QuestionNodeMapper;
import com.zhuiyi.ms.learn.mapper.QuestionRuleMapper;
import com.zhuiyi.ms.learn.service.convertor.QuestionConvertor;
import com.zhuiyi.ms.learn.enums.MsgCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;


import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 此类的描述是：
 *
 * @author crazyhu@wezhuiyi.com
 * @date 2018-09-18 14:23
 **/
@Slf4j
@Service
public class QuestionService {

    @Autowired
    private QuestionClassifyMapper questionClassifyMapper;

    @Autowired
    private QuestionInfoMapper questionInfoMapper;

    @Autowired
    private QuestionNodeMapper questionNodeMapper;

    @Autowired
    private QuestionRuleMapper questionRuleMapper;

    public BaseResponse<Long> addQuestionClassify(AddQuestionClassifyRequest request) {

        QuestionClassify questionClassify = new QuestionClassify();
        questionClassify.setClassifyName(request.getClassifyName());
        Date currentTime = new Date();
        questionClassify.setCreateTime(currentTime);
        questionClassify.setUpdateTime(currentTime);

        questionClassifyMapper.insertSelective(questionClassify);

        BaseResponse<Long> response = new BaseResponse<>(MsgCodeEnum.SUCCESS);
        response.setData(questionClassify.getId());
        return response;
    }

    public BaseResponse<String> updateQuestionClassify(UpdateQuestionClassifyRequest request) {

        QuestionClassify questionClassify = questionClassifyMapper.selectByPrimaryKey(request.getClassifyId());
        if (questionClassify != null) {
            questionClassify.setClassifyName(request.getClassifyName());
            questionClassify.setUpdateTime(new Date());
            questionClassifyMapper.updateByPrimaryKeySelective(questionClassify);
        }

        BaseResponse<String> response = new BaseResponse<>(MsgCodeEnum.SUCCESS);
        return response;
    }


    public BaseResponse<QuestionClassifyListResponse> questionClassifyList(BaseRequest request) {
        Page page = Page.toPage(request.getPageNo(), request.getPageSize());

        List<QuestionClassifyVo> list = questionClassifyMapper.queryList(page);

        List<QuestionClassifyDto> dataList = Optional.ofNullable(list).orElse(Collections.emptyList())
                .stream().map(vo -> {
                    QuestionClassifyDto dto = new QuestionClassifyDto();
                    dto.setClassifyId(vo.getClassifyId());
                    dto.setClassifyName(vo.getClassifyName());
                    dto.setUsedCount(Optional.ofNullable(vo.getUserCount()).orElse(0));
                    return dto;
                }).collect(Collectors.toList());

        BaseResponse<QuestionClassifyListResponse> response = new BaseResponse<>();
        QuestionClassifyListResponse data = new QuestionClassifyListResponse();
        data.setDataList(dataList);
        response.setData(data);
        return response;
    }

    public BaseResponse<String> deleteQuestionClassify(DeleteQuestionClassifyRequest request) {
        questionClassifyMapper.deleteByPrimaryKey(request.getClassifyId());
        BaseResponse<String> response = new BaseResponse<>(MsgCodeEnum.SUCCESS);
        return response;
    }

    @Transactional(rollbackFor = Exception.class)
    public BaseResponse<Long> addQuestion(AddQuestionRequest request) {

        // 添加题目基本信息
        Date currentTime = new Date();
        QuestionInfo questionInfo = QuestionConvertor.toQuestionInfo(request, currentTime);
        questionInfoMapper.insertSelective(questionInfo);
        final Long questionId = questionInfo.getId();

        // 添加全局规则
        List<QuestionRule> questionRuleList = Optional.ofNullable(request.getGlobalRuleIdList()).orElse(Collections.emptyList())
                .stream().map(globalRuleId -> QuestionConvertor.toQuestionRule(globalRuleId, questionId, currentTime))
                .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(questionRuleList)) {
            questionRuleMapper.batchInsert(questionRuleList);
        }

//        // 添加节点
//        List<QuestionNode> nodeList = Optional.ofNullable(request.getNodeList()).orElse(Collections.emptyList())
//                .stream().map(item -> QuestionConvertor.toQuestionNode(item, questionId, currentTime))
//                .collect(Collectors.toList());
//        if (!CollectionUtils.isEmpty(nodeList)) {
//            questionNodeMapper.batchInsert(nodeList);
//        }

        BaseResponse<Long> response = new BaseResponse<>(MsgCodeEnum.SUCCESS);
        response.setData(questionInfo.getId());
        return response;
    }

    @Transactional(rollbackFor = Exception.class)
    public BaseResponse<String> updateQuestion(UpdateQuestionRequest request) {
        // 更新题目基本信息
        Date currentTime = new Date();
        QuestionInfo questionInfo = QuestionConvertor.toQuestionInfo(request, currentTime);
        questionInfoMapper.updateByPrimaryKeySelective(questionInfo);
        final Long questionId = request.getQuestionId();
        /*
         * 更新全局规则，先删除原来的，再插入新的
         */
        questionRuleMapper.deleteByQuestionId(questionId);
        List<QuestionRule> questionRuleList = Optional.ofNullable(request.getGlobalRuleIdList()).orElse(Collections.emptyList())
                .stream().map(globalRuleId -> QuestionConvertor.toQuestionRule(globalRuleId, questionId, currentTime))
                .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(questionRuleList)) {
            questionRuleMapper.batchInsert(questionRuleList);
        }

        // 更新节点，先删除原来的，再插入新的
//        questionNodeMapper.deleteByQuestionId(questionId);
//        List<QuestionNode> nodeList = Optional.ofNullable(request.getNodeList()).orElse(Collections.emptyList())
//                .stream().map(item -> QuestionConvertor.toQuestionNode(item, questionId, currentTime))
//                .collect(Collectors.toList());
//        if (!CollectionUtils.isEmpty(nodeList)) {
//            questionNodeMapper.batchInsert(nodeList);
//        }

        return new BaseResponse<>(MsgCodeEnum.SUCCESS);
    }

    /**
     * 查询题目列表
     */
    public BaseResponse<QueryQuestionListResponse> queryQuestionList(QueryQuestionListRequest request) {
        // 设置查询条件
        Page page = Page.toPage(request.getPageNo(), request.getPageSize());
        QuestionFilter filter = new QuestionFilter();
        filter.setQuestionClassifyId(request.getQuestionClassifyId());
        filter.setQuestionGrade(request.getQuestionGrade());
        if (StringUtils.isNumeric(request.getIdOrName())) {
            filter.setQuestionId(Long.valueOf(request.getIdOrName()));
        } else {
            filter.setQuestionName(request.getIdOrName());
        }
        filter.setDeleteStatus(DeleteStatus.UN_DELETE.value());

        // 查询列表和Count
        List<QuestionInfo> infoList = questionInfoMapper.selectByFilter(filter, page);
        Long count = questionInfoMapper.selectCountByFilter(filter, page);

        // 查询结果转换DTO
        List<QuestionInfoDto> dataList = Optional.ofNullable(infoList).orElse(Collections.emptyList())
                .stream().map(QuestionConvertor::toQuestionInfoDto).collect(Collectors.toList());

        QueryQuestionListResponse data = new QueryQuestionListResponse();
        data.setCount(count);
        data.setDataList(dataList);
        BaseResponse<QueryQuestionListResponse> response = new BaseResponse<>(MsgCodeEnum.SUCCESS);
        response.setData(data);
        return response;
    }

    /**
     * 查询题目详情
     */
    public BaseResponse<QuestionInfoDto> queryQuestion(QueryQuestionRequest request) {
        QuestionInfo questionInfo = questionInfoMapper.selectByPrimaryKey(request.getQuestionId());

        BaseResponse<QuestionInfoDto> response = new BaseResponse<>(MsgCodeEnum.SUCCESS);
        if (questionInfo != null) {
            QuestionInfoDto data = QuestionConvertor.toQuestionInfoDto(questionInfo);
            // 查询全局规则
            List<Long> globalRuleIdList = questionRuleMapper.selectByQuestionId(request.getQuestionId());
            data.setGlobalRuleIdList(globalRuleIdList);
            response.setData(data);
        }
        return response;
    }

    /**
     * 删除题目
     */
    public BaseResponse<String> deleteQuestion(DeleteQuestionRequest request) {
        QuestionInfo questionInfo = new QuestionInfo();
        questionInfo.setId(request.getQuestionId());
        questionInfo.setDeleteStatus(DeleteStatus.DELETED.value());
        questionInfo.setUpdateTime(new Date());
        questionInfoMapper.updateByPrimaryKeySelective(questionInfo);
        BaseResponse<String> response = new BaseResponse<>(MsgCodeEnum.SUCCESS);
        return response;
    }

    /**
     * 添加节点
     */
    public BaseResponse<Long> addQuestionNode(AddQuestionNodeRequest request) {
        Date currentTime = new Date();
        QuestionNode questionNode = QuestionConvertor.toQuestionNode(request, currentTime);
        questionNodeMapper.insertSelective(questionNode);

        BaseResponse<Long> response = new BaseResponse<>(MsgCodeEnum.SUCCESS);
        response.setData(questionNode.getId());
        return response;
    }

    /**
     * 更新节点
     */
    public BaseResponse<Long> updateQuestionNode(UpdateQuestionNodeRequest request) {
        Date currentTime = new Date();
        QuestionNode questionNode = QuestionConvertor.toQuestionNode(request, currentTime);
        questionNodeMapper.updateByPrimaryKeySelective(questionNode);

        BaseResponse<Long> response = new BaseResponse<>(MsgCodeEnum.SUCCESS);
        return response;
    }

    /**
     * 查询节点
     */
    public BaseResponse<QuestionNodeDto> queryQuestionNode(QueryQuestionNodeRequest request) {
        QuestionNode questionNode = questionNodeMapper.selectByPrimaryKey(request.getNodeId());

        BaseResponse<QuestionNodeDto> response = new BaseResponse<>(MsgCodeEnum.SUCCESS);
        if (questionNode != null) {
            QuestionNodeDto dto = QuestionConvertor.toQuestionNodeDto(questionNode);
            response.setData(dto);
        }
        return response;
    }

    /**
     * 删除节点
     */
    public BaseResponse<Long> deleteQuestionNode(DeleteQuestionNodeRequest request) {
        QuestionNode questionNode = new QuestionNode();
        questionNode.setId(request.getNodeId());
        questionNode.setDeleteStatus(DeleteStatus.DELETED.value());
        questionNode.setUpdateTime(new Date());
        questionNodeMapper.updateByPrimaryKeySelective(questionNode);

        return new BaseResponse<>(MsgCodeEnum.SUCCESS);
    }

}
