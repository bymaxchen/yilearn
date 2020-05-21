package com.zhuiyi.ms.learn.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhuiyi.ms.learn.common.enums.TrainTaskStatus;
import com.zhuiyi.ms.learn.entity.*;
import com.zhuiyi.ms.learn.entity.bo.ResponsePage;
import com.zhuiyi.ms.learn.entity.vo.req.ExamSessionReqVO;
import com.zhuiyi.ms.learn.entity.vo.res.ExamSessionResVO;
import com.zhuiyi.ms.learn.entity.vo.res.SceneQuestionResVO;
import com.zhuiyi.ms.learn.mapper.TExamSessionMapper;
import com.zhuiyi.ms.learn.service.api.ITExamSessionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhuiyi.ms.learn.util.PageBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bymax
 * @since 2019-12-31
 */
@DS("#session.dataSourceName")
@Service
public class TExamSessionServiceImpl extends ServiceImpl<TExamSessionMapper, TExamSession> implements ITExamSessionService {
    @Resource
    private TChallengeMissionServiceImpl challengeMissionService;

    @Resource
    private TBaseQuestionServiceImpl tBaseQuestionService;

    @Resource
    private TExamTrainingServiceImpl tExamTrainingService;

    @Resource
    private TSceneQuestionServiceImpl tSceneQuestionService;

    // TODO 增加请求查询字段
    @Override
    public ResponsePage findAll(ExamSessionReqVO examSessionReqVO) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(examSessionReqVO.getTaskId() != null, "taskId", examSessionReqVO.getTaskId());
        queryWrapper.eq(examSessionReqVO.getSessionId() != null, "sessionId", examSessionReqVO.getSessionId());
        queryWrapper.eq(examSessionReqVO.getTaskType() != null, "taskType", examSessionReqVO.getTaskType());
        queryWrapper.eq(examSessionReqVO.getAgentId() != null, "agentId", examSessionReqVO.getAgentId());
        queryWrapper.eq(examSessionReqVO.getTrainingType() != null, "trainingType", examSessionReqVO.getTrainingType());
        queryWrapper.eq(examSessionReqVO.getExamQuestionTypeId() != null, "examQuestionTypeId", examSessionReqVO.getExamQuestionTypeId());
        queryWrapper.eq(examSessionReqVO.getMode() != null, "mode", examSessionReqVO.getMode());

        queryWrapper.orderByDesc("create_time");

        if (examSessionReqVO.getScore() == null) {
            queryWrapper.ne("score", -1);
        } else {
            queryWrapper.eq("score", examSessionReqVO.getScore());
        }



        Page page = examSessionReqVO.getPage();
        if (page == null) {
            page = PageBuilder.getMaxPage();
        }

        IPage iPage = this.page(page, queryWrapper);

        List<TExamSession> tExamSessionList = iPage.getRecords();

        List<SceneQuestionResVO> sceneQuestionResVOList = tSceneQuestionService.findAll(tExamSessionList.stream().map(item -> item.getExamId()).collect(Collectors.toList()));

        for(TExamSession tExamSession: tExamSessionList) {
            for(SceneQuestionResVO sceneQuestionResVO: sceneQuestionResVOList) {
                SceneQuestionResVO temp = new SceneQuestionResVO();
                BeanUtils.copyProperties(sceneQuestionResVO, temp);

                if (tExamSession.getExamId().equals(sceneQuestionResVO.getBaseQuestionId())) {
                    tExamSession.setDifficultyDegree(sceneQuestionResVO.getLevel());

                    tExamSession.setExam(temp);
                }
            }
        }

        if (examSessionReqVO.getDifficultyDegree() != null) {
            tExamSessionList = tExamSessionList.stream().filter(item -> item.getDifficultyDegree().equals(examSessionReqVO.getDifficultyDegree())).collect(Collectors.toList());
            iPage.setRecords(tExamSessionList);
        }


        return PageBuilder.buildResponsePage(iPage);
    }

    @Override
    public Boolean update(Integer id, ExamSessionReqVO examSessionReqVO) {
        TExamSession tExamSession = new TExamSession();
        BeanUtils.copyProperties(examSessionReqVO, tExamSession);
        tExamSession.setId(id);
        return this.updateById(tExamSession);
    }

    @Override
    public List<ExamSessionResVO> getChallengeMissionExam(Integer agentId) {
        // 获取当前用户的冠军挑战赛的session数据
        QueryWrapper queryWrapper = new QueryWrapper();

        queryWrapper.eq("agentId", agentId);
        queryWrapper.eq("taskType", 2);
        queryWrapper.ne("score", -1);
        queryWrapper.orderByDesc("create_time");
        List<TExamSession> examSessionList = this.list(queryWrapper);

        // 获取冠军挑战赛的id，且获取冠军挑战赛已完成的数据
        QueryWrapper challengeMissionWrapper = new QueryWrapper();
        List<Integer> challengeIds = examSessionList.stream().map(item -> item.getTaskId()).collect(Collectors.toList());

        if (!challengeIds.isEmpty()) {
            challengeMissionWrapper.in("id", challengeIds);
        }


        challengeMissionWrapper.eq("status", 3);
        List<TChallengeMission> challengeMissionList = challengeMissionService.list(challengeMissionWrapper);

        // 最后拿到已完成的冠军挑战赛id，用于获取最后排序的数据
        List<Integer> finalIds = challengeMissionList.stream().map(item -> item.getId()).collect(Collectors.toList());

        QueryWrapper queryWrapper1 = new QueryWrapper();

        if (!finalIds.isEmpty()) {
            queryWrapper1.in("taskId", finalIds);
        }


        queryWrapper1.eq("taskType", 2);
        queryWrapper1.orderByDesc("taskId");
        queryWrapper1.orderByDesc("score");


        List<TExamSession> allExamSession = this.list(queryWrapper1);

        Integer currentTaskId = -1; // 当前任务id
        Integer examIndex = 0; // 当前任务指针，用作排名


        List<ExamSessionResVO> examSessionResVOList = examSessionList.stream().map(item -> {
            ExamSessionResVO examSessionResVO = new ExamSessionResVO();
            BeanUtils.copyProperties(item, examSessionResVO);
            return examSessionResVO;
        }).collect(Collectors.toList());

        for(TExamSession session: allExamSession) {
            if (!currentTaskId.equals(session.getTaskId())) {
                currentTaskId = session.getTaskId();
                examIndex = 0;
            }
            examIndex++;

            // 如果指针指向的是当前用户的数据，记录到examSession
            if (session.getAgentId().equals(agentId)) {
                ExamSessionResVO target = examSessionResVOList.stream().filter(item -> item.getTaskId().equals(session.getTaskId())).findAny().orElse(null);

                if (target != null) {
                    target.setRange(examIndex);
                    TChallengeMission tChallengeMission = challengeMissionList.stream().filter(mission -> mission.getId().equals(session.getTaskId())).findAny().orElse(null);
                    if (tChallengeMission != null) {
                        target.setChallengeExamName(tChallengeMission.getChallengeExamName());
                    }

                }
            }

        }

        return examSessionResVOList;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean finishSession(ExamSessionReqVO examSessionReqVO) {
        QueryWrapper queryWrapper = new QueryWrapper();

        Integer taskId = examSessionReqVO.getTaskId();
        Integer taskType = examSessionReqVO.getTaskType();

        queryWrapper.eq("taskId", examSessionReqVO.getTaskId());
        queryWrapper.eq("agentId", examSessionReqVO.getAgentId());
        queryWrapper.eq("taskType", examSessionReqVO.getTaskType());

        TExamSession tExamSession = new TExamSession();
        BeanUtils.copyProperties(examSessionReqVO, tExamSession);
        tExamSession.setStatus(1);
        Boolean isSuccess = this.update(tExamSession, queryWrapper);

        QueryWrapper listQuery = new QueryWrapper();
        listQuery.eq("taskId", examSessionReqVO.getTaskId());
        listQuery.eq("taskType", examSessionReqVO.getTaskType());
        List<TExamSession> examSessionList = this.list(listQuery);

        int doneCnt = 0;
        for(TExamSession session : examSessionList) {
            if (session.getStatus().equals(1)) {
                doneCnt++;
            }
        }
        Integer newStatus;
        if (doneCnt == examSessionList.size()) {
            newStatus = TrainTaskStatus.DONE.value();
        } else {
            newStatus = TrainTaskStatus.DOING.value();
        }


        // TODO 需要把魔术字符串去掉以及抽出成通用逻辑
        Integer status;
        ServiceImpl service;
        Object entity ;
        if (taskType.equals(1)) {
            service = tExamTrainingService;
            status = Optional.ofNullable(tExamTrainingService.getById(taskId)).orElse(new TExamTraining()).getStatus();
            TExamTraining examTraining = new TExamTraining();
            examTraining.setId(taskId);
            examTraining.setStatus(newStatus);
            entity = examTraining;
        } else {
            service = challengeMissionService;
            status = Optional.ofNullable(challengeMissionService.getById(taskId)).orElse(new TChallengeMission()).getStatus();
            TChallengeMission challengeMission = new TChallengeMission();
            challengeMission.setId(taskId);
            challengeMission.setStatus(newStatus);
            entity = challengeMission;
        }


         service.updateById(entity);

        return isSuccess;
    }
}
