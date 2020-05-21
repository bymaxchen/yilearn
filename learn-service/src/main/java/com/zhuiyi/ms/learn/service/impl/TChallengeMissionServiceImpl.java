package com.zhuiyi.ms.learn.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhuiyi.ms.learn.common.enums.TrainTaskStatus;
import com.zhuiyi.ms.learn.entity.TChallengeExam;
import com.zhuiyi.ms.learn.entity.TChallengeMission;
import com.zhuiyi.ms.learn.entity.TExamSession;
import com.zhuiyi.ms.learn.entity.bo.ResponsePage;
import com.zhuiyi.ms.learn.entity.bo.SceneQuestionBO;
import com.zhuiyi.ms.learn.entity.vo.req.ChallengeMissionReqVO;
import com.zhuiyi.ms.learn.entity.vo.res.TChallengeMissionResVO;
import com.zhuiyi.ms.learn.mapper.TChallengeMissionMapper;
import com.zhuiyi.ms.learn.service.api.ITChallengeMissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhuiyi.ms.learn.util.PageBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
public class TChallengeMissionServiceImpl extends ServiceImpl<TChallengeMissionMapper, TChallengeMission> implements ITChallengeMissionService {
    @Resource
    private TExamSessionServiceImpl tExamSessionService;

    @Resource
    private TChallengeExamServiceImpl tChallengeExamService;


    @Override
    public ResponsePage findAll(ChallengeMissionReqVO challengeMissionReqVO) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if (StringUtils.isNotEmpty(challengeMissionReqVO.getKeyword())) {
            queryWrapper.like("challengeExamName", challengeMissionReqVO.getKeyword());
        }

        if (challengeMissionReqVO.getStatus() != null) {
            queryWrapper.eq("status", challengeMissionReqVO.getStatus());
        }

        queryWrapper.orderByDesc("update_time");

        Page page = challengeMissionReqVO.getPage();
        if (page == null) {
            page = PageBuilder.getMaxPage();
        }

        IPage iPage = this.page(page, queryWrapper);

        List<TChallengeMission> challengeMissionList = iPage.getRecords();


        QueryWrapper queryWrapper1 = new QueryWrapper();


        List<Integer> challengeMissionIds = challengeMissionList.stream().map(item -> item.getId()).collect(Collectors.toList());
        queryWrapper1.eq("taskType", 2);

        if (!challengeMissionIds.isEmpty()) {
            queryWrapper1.in("taskId", challengeMissionIds);
        }


        List<TExamSession> examSessionList = tExamSessionService.list(queryWrapper1);

        List<TChallengeMissionResVO> challengeMissionResVOList = new ArrayList<>();
        for(TChallengeMission tChallengeMission: challengeMissionList) {
            List<String> agents = new ArrayList<>(Arrays.asList(tChallengeMission.getAgentList().split(",")));
            List<TExamSession> sessions = new ArrayList<>();

            TChallengeMissionResVO tChallengeMissionResVO = new TChallengeMissionResVO();
            BeanUtils.copyProperties(tChallengeMission, tChallengeMissionResVO);


            for(TExamSession session : examSessionList) {
                if (session.getTaskId().equals(tChallengeMission.getId()) && agents.contains(session.getAgentId().toString())) {
                    sessions.add(session);
                }
            }

            tChallengeMissionResVO.setExamSessionList(sessions);
            challengeMissionResVOList.add(tChallengeMissionResVO);
        }
        return PageBuilder.buildResponsePage(challengeMissionResVOList, iPage.getTotal());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TChallengeMission create(ChallengeMissionReqVO challengeMissionReqVO) {
        TChallengeMission tChallengeMission = new TChallengeMission();

        BeanUtils.copyProperties(challengeMissionReqVO, tChallengeMission);
        tChallengeMission.setAgentList(StringUtils.join(challengeMissionReqVO.getExamSessionList().stream().map(item -> item.getAgentId()).collect(Collectors.toList()), ","));
        this.save(tChallengeMission);

        TChallengeExam tChallengeExam = tChallengeExamService.getById(challengeMissionReqVO.getChallengeExamId());
        List<SceneQuestionBO> sceneQuestionBOList = JSON.parseArray(tChallengeExam.getExamList(), SceneQuestionBO.class);

        Collections.shuffle(sceneQuestionBOList);

        Integer size = sceneQuestionBOList.size();

        Integer index = 0;
        for(TExamSession tExamSession: challengeMissionReqVO.getExamSessionList()) {
            SceneQuestionBO sceneQuestionBO = sceneQuestionBOList.get(index % size);
            tExamSession.setScore(-1);
            tExamSession.setTaskType(2);
            tExamSession.setTaskId(tChallengeMission.getId());
            tExamSession.setExamId(sceneQuestionBO.getId());
            tExamSession.setExamName(sceneQuestionBO.getQuestionTitle());
            tExamSession.setStartTime(tChallengeMission.getStartTime());

        }

        tExamSessionService.saveBatch(challengeMissionReqVO.getExamSessionList());


        return tChallengeMission;
    }



    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean delete(Integer id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("taskId", id);
        queryWrapper.eq("taskType", 2);

        tExamSessionService.remove(queryWrapper);
        return this.removeById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean update(Integer id, ChallengeMissionReqVO challengeMissionReqVO) {
        TChallengeMission tChallengeMission = new TChallengeMission();
        BeanUtils.copyProperties(challengeMissionReqVO, tChallengeMission);
        tChallengeMission.setId(id);

        if (!challengeMissionReqVO.getExamSessionList().isEmpty()) {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("taskId", id);
            queryWrapper.eq("taskType", 2);
            List<TExamSession> examSessionList = tExamSessionService.list(queryWrapper);

            List<Integer> currentAgentIds = examSessionList.stream().map(item -> item.getAgentId()).collect(Collectors.toList());
            List<Integer> newAgentIds = challengeMissionReqVO.getExamSessionList().stream().map(item -> item.getAgentId()).collect(Collectors.toList());

            List<Integer> deletedAgentIds = new ArrayList<>();
            List<TExamSession> newAgents = new ArrayList<>();

            for(Integer currentAgentId : currentAgentIds) {
                if (!newAgentIds.contains(currentAgentId)) {
                    deletedAgentIds.add(currentAgentId);
                }
            }

            for(Integer newAgentId : newAgentIds) {
                if (!currentAgentIds.contains(newAgentId)) {
                    for(TExamSession examSession: challengeMissionReqVO.getExamSessionList()) {
                        if (examSession.getAgentId().equals(newAgentId)) {
                            TExamSession tempExamSession = new TExamSession();
                            BeanUtils.copyProperties(examSession, tempExamSession);
                            tempExamSession.setScore(0);
                            tempExamSession.setTaskId(id);
                            newAgents.add(tempExamSession);
                        }
                    }
                }
            }
            QueryWrapper removeWrapper = new QueryWrapper();
            if (!deletedAgentIds.isEmpty()) {
                removeWrapper.in("agentId", deletedAgentIds);
            }


            removeWrapper.eq("taskId", id);
            tExamSessionService.remove(removeWrapper);
            tChallengeMission.setAgentList(StringUtils.join(newAgentIds, ','));
        }

        return this.updateById(tChallengeMission);
    }
}
