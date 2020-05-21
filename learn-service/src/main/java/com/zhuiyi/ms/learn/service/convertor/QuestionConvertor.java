package com.zhuiyi.ms.learn.service.convertor;

import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.zhuiyi.ms.learn.common.Constants;
import com.zhuiyi.ms.learn.common.enums.DeleteStatus;
import com.zhuiyi.ms.learn.dto.request.AddQuestionNodeRequest;
import com.zhuiyi.ms.learn.dto.request.AddQuestionRequest;
import com.zhuiyi.ms.learn.dto.request.UpdateQuestionNodeRequest;
import com.zhuiyi.ms.learn.dto.request.UpdateQuestionRequest;
import com.zhuiyi.ms.learn.dto.transfer.QuestionInfoDto;
import com.zhuiyi.ms.learn.dto.transfer.QuestionNodeDto;
import com.zhuiyi.ms.learn.entity.QuestionInfo;
import com.zhuiyi.ms.learn.entity.QuestionNode;
import com.zhuiyi.ms.learn.entity.QuestionRule;
import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 此类的描述是：
 *
 * @author crazyhu@wezhuiyi.com
 * @date 2018-09-18 17:47
 **/
public class QuestionConvertor {

    public static QuestionInfo toQuestionInfo(AddQuestionRequest request, Date currentTime) {
        QuestionInfo questionInfo = new QuestionInfo();
        questionInfo.setQuestionName(request.getQuestionName());
        questionInfo.setQuestionClassifyId(request.getQuestionClassifyId());
        questionInfo.setQuestionGrade(request.getQuestionGrade());
        questionInfo.setIsForceOrder(request.getIsForceOrder());
        questionInfo.setCustomerName(request.getCustomerName());
        questionInfo.setCustomerGender(request.getCustomerGender());
        questionInfo.setCustomerBankCard(request.getCustomerBankCard());
        questionInfo.setCustomerArrears(request.getCustomerArrears());
        questionInfo.setCustomerMinRepayment(request.getCustomerMinRepayment());
        questionInfo.setCustomerOverdue(request.getCustomerOverdue());
        questionInfo.setCustomerDelayStage(request.getCustomerDelayStage());
        questionInfo.setCreateTime(currentTime);
        questionInfo.setUpdateTime(currentTime);
        return questionInfo;
    }

    public static QuestionInfo toQuestionInfo(UpdateQuestionRequest request, Date currentTime) {
        QuestionInfo questionInfo = new QuestionInfo();
        questionInfo.setId(request.getQuestionId());
        questionInfo.setQuestionName(request.getQuestionName());
        questionInfo.setQuestionClassifyId(request.getQuestionClassifyId());
        questionInfo.setQuestionGrade(request.getQuestionGrade());
        questionInfo.setIsForceOrder(request.getIsForceOrder());
        questionInfo.setCustomerName(request.getCustomerName());
        questionInfo.setCustomerGender(request.getCustomerGender());
        questionInfo.setCustomerBankCard(request.getCustomerBankCard());
        questionInfo.setCustomerArrears(request.getCustomerArrears());
        questionInfo.setCustomerMinRepayment(request.getCustomerMinRepayment());
        questionInfo.setCustomerOverdue(request.getCustomerOverdue());
        questionInfo.setCustomerDelayStage(request.getCustomerDelayStage());
        questionInfo.setUpdateTime(currentTime);
        return questionInfo;
    }

    public static QuestionNode toQuestionNode(QuestionNodeDto item, Long questionId, Date currentTime) {
        QuestionNode questionNode = new QuestionNode();
        questionNode.setQuestionId(questionId);
        questionNode.setNodeType(item.getNodeType());
        questionNode.setNodeName(item.getNodeName());
        questionNode.setSequenceNo(item.getSequenceNo());
        questionNode.setScaftType(item.getScaftType());
        questionNode.setIsInterrupt(item.getIsInterrupt());
        questionNode.setInterruptTime(item.getInterruptTime());
        questionNode.setIsHangup(item.getIsHangup());
        questionNode.setSemanticsId(item.getSemanticsId());
        if (item.getCustomReplyList() != null) {
            questionNode.setCustomReply(JSON.toJSONString(item.getCustomReplyList()));
        }
        questionNode.setRuleId(item.getRuleId());
        questionNode.setCreateTime(currentTime);
        questionNode.setUpdateTime(currentTime);
        questionNode.setDeleteStatus(DeleteStatus.UN_DELETE.value());
        return questionNode;
    }

    public static QuestionRule toQuestionRule(Long globalRuleId, Long questionId, Date currentTime) {
        QuestionRule rule = new QuestionRule();
        rule.setGlobalRuleId(globalRuleId);
        rule.setQuestionId(questionId);
        rule.setCreateTime(currentTime);
        return rule;
    }

    public static QuestionInfoDto toQuestionInfoDto(QuestionInfo questionInfo) {
        QuestionInfoDto dto = new QuestionInfoDto();
        dto.setQuestionId(questionInfo.getId());
        dto.setQuestionName(questionInfo.getQuestionName());
        dto.setQuestionClassifyId(questionInfo.getQuestionClassifyId());
        dto.setQuestionGrade(questionInfo.getQuestionGrade());
        dto.setIsForceOrder(questionInfo.getIsForceOrder());
        dto.setCustomerName(questionInfo.getCustomerName());
        dto.setCustomerGender(questionInfo.getCustomerGender());
        dto.setCustomerBankCard(questionInfo.getCustomerBankCard());
        dto.setCustomerArrears(questionInfo.getCustomerArrears());
        dto.setCustomerMinRepayment(questionInfo.getCustomerMinRepayment());
        dto.setCustomerOverdue(questionInfo.getCustomerOverdue());
        dto.setCustomerDelayStage(questionInfo.getCustomerDelayStage());
        dto.setCreateTime(DateFormatUtils.format(questionInfo.getCreateTime(), Constants.DATE_TIME_FORMAT));
        dto.setUpdateTime(DateFormatUtils.format(questionInfo.getUpdateTime(), Constants.DATE_TIME_FORMAT));
        return dto;
    }

    public static QuestionNode toQuestionNode(AddQuestionNodeRequest request, Date currentTime) {
        QuestionNode questionNode = new QuestionNode();
        questionNode.setQuestionId(request.getQuestionId());
        questionNode.setNodeType(request.getNodeType());
        questionNode.setNodeName(request.getNodeName());
        questionNode.setSequenceNo(request.getSequenceNo());
        questionNode.setScaftType(request.getScaftType());
        questionNode.setIsInterrupt(request.getIsInterrupt());
        questionNode.setInterruptTime(request.getInterruptTime());
        questionNode.setIsHangup(request.getIsHangup());
        questionNode.setSemanticsId(request.getSemanticsId());
        if (request.getCustomReplyList() != null) {
            questionNode.setCustomReply(JSON.toJSONString(request.getCustomReplyList()));
        }
        questionNode.setRuleId(request.getRuleId());
        questionNode.setCreateTime(currentTime);
        questionNode.setUpdateTime(currentTime);
        questionNode.setDeleteStatus(DeleteStatus.UN_DELETE.value());
        return questionNode;
    }

    public static QuestionNode toQuestionNode(UpdateQuestionNodeRequest request, Date currentTime) {
        QuestionNode questionNode = new QuestionNode();
        questionNode.setQuestionId(request.getQuestionId());
        questionNode.setNodeType(request.getNodeType());
        questionNode.setNodeName(request.getNodeName());
        questionNode.setSequenceNo(request.getSequenceNo());
        questionNode.setScaftType(request.getScaftType());
        questionNode.setIsInterrupt(request.getIsInterrupt());
        questionNode.setInterruptTime(request.getInterruptTime());
        questionNode.setIsHangup(request.getIsHangup());
        questionNode.setSemanticsId(request.getSemanticsId());
        if (request.getCustomReplyList() != null) {
            questionNode.setCustomReply(JSON.toJSONString(request.getCustomReplyList()));
        }
        questionNode.setRuleId(request.getRuleId());
        questionNode.setUpdateTime(currentTime);
        questionNode.setDeleteStatus(DeleteStatus.UN_DELETE.value());
        return questionNode;
    }

    public static QuestionNodeDto toQuestionNodeDto(QuestionNode questionNode) {
        QuestionNodeDto questionNodeDto= new QuestionNodeDto();
        questionNodeDto.setQuestionNodeId(questionNode.getId());
        questionNodeDto.setQuestionId(questionNode.getQuestionId());
        questionNodeDto.setNodeType(questionNode.getNodeType());
        questionNodeDto.setNodeName(questionNode.getNodeName());
        questionNodeDto.setSequenceNo(questionNode.getSequenceNo());
        questionNodeDto.setScaftType(questionNode.getScaftType());
        questionNodeDto.setIsInterrupt(questionNode.getIsInterrupt());
        questionNodeDto.setInterruptTime(questionNode.getInterruptTime());
        questionNodeDto.setIsHangup(questionNode.getIsHangup());
        questionNodeDto.setSemanticsId(questionNode.getSemanticsId());
        questionNodeDto.setRuleId(questionNode.getRuleId());
        if (questionNode.getCustomReply() != null) {
            questionNodeDto.setCustomReplyList(JSON.parseArray(questionNode.getCustomReply(), String.class));
        }
        return questionNodeDto;
    }
}
