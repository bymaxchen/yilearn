package com.zhuiyi.ms.learn.service.impl;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhuiyi.ms.learn.common.enums.PaperEnum;
import com.zhuiyi.ms.learn.entity.*;
import com.zhuiyi.ms.learn.entity.bo.ExamBO;
import com.zhuiyi.ms.learn.entity.bo.ExamPartBO;
import com.zhuiyi.ms.learn.entity.bo.ExamPartQuestionBO;
import com.zhuiyi.ms.learn.entity.vo.req.ExamResultReqVO;
import com.zhuiyi.ms.learn.entity.vo.res.ExamResultResVO;
import com.zhuiyi.ms.learn.mapper.TExamMapper;
import com.zhuiyi.ms.learn.service.api.ITExamService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhuiyi.ms.learn.util.HttpUtil;
import com.zhuiyi.ms.learn.util.KeyUtil;
import com.zhuiyi.ms.learn.util.LoggerUtil;
import okhttp3.Headers;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bymax
 * @since 2019-10-24
 */
@DS("#session.dataSourceName")
@Service
public class TExamServiceImpl extends ServiceImpl<TExamMapper, TExam> implements ITExamService {
    @Resource
    private TExamPartServiceImpl tExamPartService;

    @Resource
    private TExamPartQuestionServiceImpl tExamPartQuestionService;

    @Resource
    private TChatLogServiceImpl tChatLogService;

    @Resource
    private TScoreDetailServiceImpl tScoreDetailService;

    @Resource
    private TBaseQuestionServiceImpl tBaseQuestionService;

    @Resource
    private TPaperServiceImpl tPaperService;

    @Resource
    private TPaperPartServiceImpl tPaperPartService;

    @Resource
    private TAudioServiceImpl tAudioService;

    @Resource
    private TSceneQuestionServiceImpl tSceneQuestionService;

    @Value("${examResultPushHost}")
    private String examResultPushHost;

    @Value("${jwtPublicKey}")
    private String jwtPublicKey;

    @Value("${jwtPrivateKey}")
    private String jwtPrivateKey;


    @Override
    public ExamBO create(ExamResultReqVO examResultReqVO) {
        TExam tExam = examResultReqVO.getOverview();
        this.save(tExam);

        List<TExamPart> tExamPartList = new ArrayList<>();

        Integer index = 0;
        for(ExamPartBO examPartBO: examResultReqVO.getPartList()) {
            TExamPart tExamPart = new TExamPart();
            BeanUtils.copyProperties(examPartBO, tExamPart);
            tExamPart.setPosition(index++);
            tExamPart.setExamId(tExam.getExamId());
            tExamPartList.add(tExamPart);
        }

        tExamPartService.saveBatch(tExamPartList);

        List<TExamPartQuestion> tExamPartQuestionList = new ArrayList<>();

        Integer i = 0;
        for(ExamPartBO examPartBO: examResultReqVO.getPartList()) {
            for(ExamPartQuestionBO examPartQuestionBO: examPartBO.getQuestionList()) {
                if(null != examPartQuestionBO){
                    TExamPartQuestion tExamPartQuestion = new TExamPartQuestion();
                    tExamPartQuestion.setExamPartId(tExamPartList.get(i).getExamPartId());
                    tExamPartQuestion.setBaseQuestionId(examPartQuestionBO.getBaseQuestionId());
                    tExamPartQuestion.setType(examPartBO.getType());
                    tExamPartQuestion.setAudioSrc(examPartQuestionBO.getAudioSrc());
                    tExamPartQuestion.setScore(examPartQuestionBO.getScore());
                    tExamPartQuestion.setEndType(examPartQuestionBO.getEndType());
                    tExamPartQuestionList.add(tExamPartQuestion);
                }
            }
            i++;
        }

        tExamPartQuestionService.saveBatch(tExamPartQuestionList);

        List<TChatLog> conversationList = new ArrayList<>();
        List<TScoreDetail> checkRules = new ArrayList<>();

        Integer j = 0;
        for(ExamPartBO examPartBO: examResultReqVO.getPartList()) {
            for(ExamPartQuestionBO examPartQuestionBO: examPartBO.getQuestionList()) {
                Integer examPartQuestionId = tExamPartQuestionList.get(j++).getExamPartQuestionId();
                for(TChatLog tChatLog: examPartQuestionBO.getConversationList()) {
                    tChatLog.setExamPartQuestionId(examPartQuestionId);
                    tChatLog.setTaskId(0L);
                    conversationList.add(tChatLog);
                }
                Integer k = 0;
                for(TScoreDetail tScoreDetail: examPartQuestionBO.getCheckRules()) {
                    tScoreDetail.setExamPartQuestionId(examPartQuestionId);
                    tScoreDetail.setPosition(k++);
                    checkRules.add(tScoreDetail);
                }
            }
        }


        tChatLogService.saveBatch(conversationList);
        tScoreDetailService.saveBatch(checkRules);

        // 保存完考试，将考试对应的音频关联
        TAudio audio = new TAudio();
        audio.setExamId(tExam.getExamId());
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("session_id", examResultReqVO.getOverview().getSessionId());
        tAudioService.update(audio, queryWrapper);

        ExamBO examBO = new ExamBO();
        BeanUtils.copyProperties(tExam, examBO);

        examBO.setIdentityCode(examResultReqVO.getIdentityCode());
        examBO.setName(examResultReqVO.getName());
        examBO.setActualScore(examResultReqVO.getOverview().getActualScore());

        return examBO;
    }

    @Override
    public ExamResultResVO findOne(String examId) {
        // 查找考试
        TExam tExam = this.getById(examId);

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("exam_id", examId);
        queryWrapper.orderByAsc("position");

        // 查找考试对应的部分
        List<TExamPart> examPartList = tExamPartService.list(queryWrapper);

        // 查找考试对应试卷
        TPaper tPaper = tPaperService.getById(tExam.getPaperId());

        // 查找试卷对应部分
        QueryWrapper queryWrapper3 = new QueryWrapper();
        queryWrapper3.eq("paper_id", tExam.getPaperId());
        List<TPaperPart> paperPartList = tPaperPartService.list(queryWrapper3);

        // 查找考试对应的音频的会话id
        TAudio audio = tAudioService.getByExamId(examId);

        ExamBO examBO = new ExamBO();
        BeanUtils.copyProperties(tPaper, examBO);
        BeanUtils.copyProperties(tExam, examBO);
        if (audio != null) {
            examBO.setSessionId(audio.getSessionId());
        }


        ExamResultResVO examResultResVO = new ExamResultResVO();
        examResultResVO.setOverview(examBO);

        List<Integer> examPartIdList = examPartList .stream().map(item -> item.getExamPartId()).collect(Collectors.toList());

        if (examPartIdList.isEmpty()) {
            examResultResVO.setPartList(new ArrayList<>());
            return examResultResVO;
        }

        QueryWrapper queryWrapper1 = new QueryWrapper();
        queryWrapper1.in("exam_part_id", examPartIdList);

        // 查找考试部分对应的试题
        List<TExamPartQuestion> tExamPartQuestionList = tExamPartQuestionService.list(queryWrapper1);


        List<Integer> baseQuestionIdList = tExamPartQuestionList.stream().map(item -> item.getBaseQuestionId()).collect(Collectors.toList());
        List<Integer> examPartQuestionIdList = tExamPartQuestionList.stream().map(item -> item.getExamPartQuestionId()).collect(Collectors.toList());


        // 获取考试试题对应的实体
        Collection<TBaseQuestion> baseQuestionList = tBaseQuestionService.listByIds(baseQuestionIdList);

        // 情景题试卷需要返回题目的详情
        HashMap<Integer, TSceneQuestion> questionId2SceneQuestion = new HashMap<>();
        if (tPaper.getType().equals(PaperEnum.SCENE_QUESTION.value())) {

            QueryWrapper queryWrapper2 = new QueryWrapper();
            queryWrapper.in("scene_question_id", baseQuestionIdList);
            Collection<TSceneQuestion> sceneQuestionList = tSceneQuestionService.list(queryWrapper2);

            for(TSceneQuestion tSceneQuestion: sceneQuestionList) {
                questionId2SceneQuestion.put(tSceneQuestion.getSceneQuestionId(), tSceneQuestion);
            }
        }

        HashMap<Integer, String> questionId2Name = new HashMap<>();
        for(TBaseQuestion tBaseQuestion: baseQuestionList) {
            questionId2Name.put(tBaseQuestion.getBaseQuestionId(), tBaseQuestion.getQuestionName());
        }

        QueryWrapper queryWrapper2 = new QueryWrapper();
        queryWrapper2.in("exam_part_question_id", examPartQuestionIdList);

        // 获取考试试题对应聊天记录
        List<TChatLog> chatLogList = tChatLogService.list(queryWrapper2);

        // 获取考试试题对应的规则得分详情
        queryWrapper2.orderByAsc("position");
        List<TScoreDetail> scoreDetailList = tScoreDetailService.list(queryWrapper2);


        List<ExamPartBO> examPartBOList = new ArrayList<>();

        for(TExamPart tExamPart: examPartList) {
            ExamPartBO examPartBO = new ExamPartBO();
            BeanUtils.copyProperties(tExamPart, examPartBO);

            List<ExamPartQuestionBO> examPartQuestionBOList = new ArrayList<>();

            for(TPaperPart tPaperPart: paperPartList) {
                if (tPaperPart.getPosition().equals(tExamPart.getPosition())) {
                    examPartBO.setPartName(tPaperPart.getPartName());
                }
            }

            for(TExamPartQuestion tExamPartQuestion: tExamPartQuestionList) {
                if (tExamPartQuestion.getExamPartId().equals(tExamPart.getExamPartId())) {
                    ExamPartQuestionBO examPartQuestionBO = new ExamPartQuestionBO();
                    examPartQuestionBO.setQuestionName(questionId2Name.get(tExamPartQuestion.getBaseQuestionId()));

                    List<TChatLog> conversationList = new ArrayList<>();
                    for(TChatLog tChatLog: chatLogList) {
                        if (tChatLog.getExamPartQuestionId().equals(tExamPartQuestion.getExamPartQuestionId())) {
                            conversationList.add(tChatLog);
                        }
                    }

                    List<TScoreDetail> checkRules = new ArrayList<>();
                    for(TScoreDetail tScoreDetail: scoreDetailList) {
                        if (tScoreDetail.getExamPartQuestionId().equals(tExamPartQuestion.getExamPartQuestionId())) {
                            checkRules.add(tScoreDetail);
                        }
                    }

                    examPartQuestionBO.setConversationList(conversationList);
                    examPartQuestionBO.setCheckRules(checkRules);
                    examPartQuestionBO.setAudioSrc(tExamPartQuestion.getAudioSrc());
                    examPartQuestionBO.setScore(tExamPartQuestion.getScore());
                    examPartQuestionBO.setEndType(tExamPartQuestion.getEndType());

                    // 情景题试卷需要返回题目的详情
                    if (tPaper.getType().equals(PaperEnum.SCENE_QUESTION.value())) {
                        TSceneQuestion sceneQuestion = questionId2SceneQuestion.get(tExamPartQuestion.getBaseQuestionId());
                        examPartQuestionBO.setCoreQuestion(sceneQuestion.getCoreQuestion());
                        examPartQuestionBO.setSceneIntro(sceneQuestion.getSceneIntro());
                    }

                    examPartQuestionBOList.add(examPartQuestionBO);
                }
            }
            examPartBO.setQuestionList(examPartQuestionBOList);
            examPartBOList.add(examPartBO);
        }

        examResultResVO.setPartList(examPartBOList);


        return examResultResVO;
    }


}
