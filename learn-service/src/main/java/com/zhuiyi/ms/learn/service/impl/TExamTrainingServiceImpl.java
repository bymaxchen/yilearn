package com.zhuiyi.ms.learn.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhuiyi.ms.learn.common.enums.TrainTaskStatus;
import com.zhuiyi.ms.learn.entity.TBaseQuestion;
import com.zhuiyi.ms.learn.entity.TExamSession;
import com.zhuiyi.ms.learn.entity.TExamTraining;
import com.zhuiyi.ms.learn.entity.bo.ResponsePage;
import com.zhuiyi.ms.learn.entity.vo.req.ExamTrainingReqVO;
import com.zhuiyi.ms.learn.entity.vo.res.ExamTrainingResVO;
import com.zhuiyi.ms.learn.mapper.TExamTrainingMapper;
import com.zhuiyi.ms.learn.service.api.ITExamTrainingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhuiyi.ms.learn.util.PageBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bymax
 * @since 2020-01-14
 */
@DS("#session.dataSourceName")
@Service
public class TExamTrainingServiceImpl extends ServiceImpl<TExamTrainingMapper, TExamTraining> implements ITExamTrainingService {
    @Resource
    private TExamSessionServiceImpl tExamSessionService;

    @Resource
    private TExamTrainingMapper tExamTrainingMapper;

    @Resource
    private TBaseQuestionServiceImpl baseQuestionService;

    // TODO 需要新的参数查询
    @Override
    public ResponsePage findAll(ExamTrainingReqVO examTrainingReqVO) {
        Page page = examTrainingReqVO.getPage();
        if (page == null) {
            page = PageBuilder.getMaxPage();
        }

        IPage<ExamTrainingResVO> iPage = tExamTrainingMapper.findAll(examTrainingReqVO.getPage(), examTrainingReqVO.getKeyword(),
                examTrainingReqVO.getAgent(), examTrainingReqVO.getStatus(), examTrainingReqVO.getLevel(), examTrainingReqVO.getQuestionCategoryId());
        List<ExamTrainingResVO> examTrainingResVOList = iPage.getRecords();

        QueryWrapper queryWrapper = new QueryWrapper();

        List<Integer> idList = examTrainingResVOList.stream().map(item -> item.getId()).collect(Collectors.toList());
        queryWrapper.in(!idList.isEmpty(), "taskId", idList);
        queryWrapper.eq("taskType", 1);
        List<TExamSession> examSessionList = tExamSessionService.list(queryWrapper);

        List<Integer> baseQuestionIdList = examTrainingResVOList.stream().map(item -> item.getExamId()).collect(Collectors.toList());
        QueryWrapper queryWrapper1 = new QueryWrapper();
        queryWrapper1.in(!baseQuestionIdList.isEmpty(), "base_question_id", baseQuestionIdList);
        List<TBaseQuestion> baseQuestionList = baseQuestionService.list(queryWrapper1);

        for(ExamTrainingResVO examTrainingResVO : examTrainingResVOList) {
            List<String> agents =  new ArrayList<>(Arrays.asList(examTrainingResVO.getAgentListStr().split(",")));
            Integer id = examTrainingResVO.getId();

            List<TExamSession> sessions = new ArrayList<>();


            for(TExamSession session : examSessionList) {
                if (session.getTaskId().equals(id) && agents.contains(session.getAgentId().toString())) {
                    sessions.add(session);
                }
            }


            examTrainingResVO.setSessions(sessions);

            for(TBaseQuestion baseQuestion: baseQuestionList) {
                if (baseQuestion.getBaseQuestionId().equals(examTrainingResVO.getExamId())) {
                    examTrainingResVO.setQuestionCategoryId(baseQuestion.getQuestionCategoryId());
                }
            }
        }

        return PageBuilder.buildResponsePage(iPage);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TExamTraining create(ExamTrainingReqVO examTrainingReqVO) {
        TExamTraining tExamTraining = new TExamTraining();
        BeanUtils.copyProperties(examTrainingReqVO, tExamTraining);
        tExamTraining.setAgentList(StringUtils.join(examTrainingReqVO.getExamSessionList().stream().map(item -> item.getAgentId()).collect(Collectors.toList()), ","));

        this.save(tExamTraining);

        examTrainingReqVO.getExamSessionList().forEach(item -> {
            item.setScore(-1);
            item.setTaskType(1);
            item.setTaskId(tExamTraining.getId());
            item.setExamId(examTrainingReqVO.getExamId());
            item.setExamName(examTrainingReqVO.getExamName());
            item.setTrainingType(examTrainingReqVO.getTrainingType());
            item.setStartTime(tExamTraining.getStartTime());
        });

        tExamSessionService.saveBatch(examTrainingReqVO.getExamSessionList());

        return tExamTraining;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean delete(Integer id) {
        TExamTraining tExamTraining = this.getById(id);

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("taskId", tExamTraining.getId());
        queryWrapper.eq("taskType", 1);

        tExamSessionService.remove(queryWrapper);

        return this.removeById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean update(Integer id, ExamTrainingReqVO examTrainingReqVO) {
        TExamTraining examTraining = new TExamTraining();
        examTraining.setId(id);
        BeanUtils.copyProperties(examTrainingReqVO, examTraining);

        if (!examTrainingReqVO.getExamSessionList().isEmpty()) {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("taskId", id);
            queryWrapper.eq("taskType", 1);
            List<TExamSession> examSessionList = tExamSessionService.list(queryWrapper);

            List<Integer> currentAgentIds = examSessionList.stream().map(item -> item.getAgentId()).collect(Collectors.toList());
            List<Integer> newAgentIds = examTrainingReqVO.getExamSessionList().stream().map(item -> item.getAgentId()).collect(Collectors.toList());

            List<Integer> deletedAgentIds = new ArrayList<>();
            List<TExamSession> newAgents = new ArrayList<>();

            for(Integer currentAgentId : currentAgentIds) {
                if (!newAgentIds.contains(currentAgentId)) {
                    deletedAgentIds.add(currentAgentId);
                }
            }

            for(Integer newAgentId : newAgentIds) {
                if (!currentAgentIds.contains(newAgentId)) {
                    for(TExamSession examSession: examTrainingReqVO.getExamSessionList()) {
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

//            tExamSessionService.remove(removeWrapper);

            if (!newAgents.isEmpty()) {
                tExamSessionService.saveBatch(newAgents);
            }



            examTraining.setAgentList(StringUtils.join(newAgentIds, ','));
            examTraining.setId(id);
        }

        return this.updateById(examTraining);
    }
}
