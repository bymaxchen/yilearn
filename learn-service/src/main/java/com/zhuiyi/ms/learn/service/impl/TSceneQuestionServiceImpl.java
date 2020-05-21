package com.zhuiyi.ms.learn.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhuiyi.ms.learn.common.enums.PaperEnum;
import com.zhuiyi.ms.learn.common.enums.QuestionsEnum;
import com.zhuiyi.ms.learn.common.enums.SceneRuleEnum;
import com.zhuiyi.ms.learn.entity.*;
import com.zhuiyi.ms.learn.entity.bo.RuleBO;
import com.zhuiyi.ms.learn.entity.vo.req.SceneQuestionReqVO;
import com.zhuiyi.ms.learn.entity.vo.res.SceneQuestionResVO;
import com.zhuiyi.ms.learn.mapper.TPaperMapper;
import com.zhuiyi.ms.learn.mapper.TSceneQuestionMapper;
import com.zhuiyi.ms.learn.service.api.ITSceneQuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bymax
 * @since 2019-10-16
 */
@Service
@DS("#session.dataSourceName")
public class TSceneQuestionServiceImpl extends ServiceImpl<TSceneQuestionMapper, TSceneQuestion> implements ITSceneQuestionService {
    @Resource
    private TBaseQuestionServiceImpl tBaseQuestionService;

    @Resource
    private TPartQuestionServiceImpl tPartQuestionService;

    @Resource
    private TPaperPartServiceImpl  tPaperPartService;

    @Resource
    private TPaperMapper tPaperMapper;

    @Resource
    private TPaperServiceImpl tPaperService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TSceneQuestion create(SceneQuestionReqVO sceneQuestionReqVO) {
        TSceneQuestion tSceneQuestion = new TSceneQuestion();

        TBaseQuestion tBaseQuestion = new TBaseQuestion();

        BeanUtils.copyProperties(sceneQuestionReqVO, tBaseQuestion);
        tBaseQuestion.setType(QuestionsEnum.SCENE_QUESTION.value());
        tBaseQuestionService.save(tBaseQuestion);

        BeanUtils.copyProperties(sceneQuestionReqVO, tSceneQuestion);
        tSceneQuestion.setSceneQuestionId(tBaseQuestion.getBaseQuestionId());

        this.save(tSceneQuestion);
        return tSceneQuestion;
    }

    @Override
    public SceneQuestionResVO findOne(Integer id) {
        TBaseQuestion tBaseQuestion = tBaseQuestionService.getById(id);

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("scene_question_id", id);

        TSceneQuestion tSceneQuestion = this.getOne(queryWrapper);

        SceneQuestionResVO sceneQuestionResVO = new SceneQuestionResVO();

        BeanUtils.copyProperties(tBaseQuestion, sceneQuestionResVO);
        BeanUtils.copyProperties(tSceneQuestion, sceneQuestionResVO);

        return sceneQuestionResVO;
    }

    @Override
    public List<SceneQuestionResVO> findAll(List<Integer> idList) {
        if (idList.isEmpty()) {
            return new ArrayList<>();
        }

        QueryWrapper queryWrapper = new QueryWrapper();

        queryWrapper.in("base_question_id", idList);

        List<TBaseQuestion> baseQuestionList = tBaseQuestionService.list(queryWrapper);

        QueryWrapper queryWrapper1 = new QueryWrapper();
        queryWrapper1.in("scene_question_id", idList);

        List<TSceneQuestion> sceneQuestionList = this.list(queryWrapper1);

        List<SceneQuestionResVO> sceneQuestionResVOList = new ArrayList<>();

        for(TBaseQuestion tBaseQuestion: baseQuestionList) {
            for (TSceneQuestion tSceneQuestion: sceneQuestionList) {
                if (tBaseQuestion.getBaseQuestionId().equals(tSceneQuestion.getSceneQuestionId())) {
                    SceneQuestionResVO sceneQuestionResVO = new SceneQuestionResVO();
                    BeanUtils.copyProperties(tBaseQuestion, sceneQuestionResVO);
                    BeanUtils.copyProperties(tSceneQuestion, sceneQuestionResVO);

                    sceneQuestionResVOList.add(sceneQuestionResVO);
                }
            }
        }
        return sceneQuestionResVOList;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean updateById(Integer id, SceneQuestionReqVO sceneQuestionReqVO) {
        TBaseQuestion tBaseQuestion = new TBaseQuestion();
        BeanUtils.copyProperties(sceneQuestionReqVO, tBaseQuestion);
        tBaseQuestion.setBaseQuestionId(id);

        tBaseQuestionService.updateById(tBaseQuestion);

        TSceneQuestion tSceneQuestion = new TSceneQuestion();
        BeanUtils.copyProperties(sceneQuestionReqVO, tSceneQuestion);
        tSceneQuestion.setSceneQuestionId(id);

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("scene_question_id", id);

//         如果情景题的流程节点发生了变化，需要更新情景题对应的试卷
        if (sceneQuestionReqVO.getIsProcessChange() != null && sceneQuestionReqVO.getIsProcessChange()) {
            this.updatePaper(id, sceneQuestionReqVO.getProcessRules());
        }

        return this.update(tSceneQuestion, queryWrapper);
    }

    /**
     *  更新情景题对应的试卷
     *  如果情景题的流程节点发生了变化，则关联的试卷的分数需要重新分配
     *  如果之前情景题有5个节点，每个20分；当变成了4个节点，则每个25分  满分100分
     */
    public void updatePaper(Integer sceneQuestionId, String processRules) {
        QueryWrapper queryWrapper = new QueryWrapper();

        queryWrapper.eq("part_question_id", sceneQuestionId);
        List<TPartQuestion> partQuestionList = tPartQuestionService.list(queryWrapper);

        if (partQuestionList.isEmpty()) return;

        List<RuleBO> ruleList = JSON.parseArray(processRules, RuleBO.class);

        Long clientNodeCount = ruleList.stream().filter(item -> item.getNodeType().equals(SceneRuleEnum.SERVICE_NODE_TYPE.value())).count();

        Long score = Math.round(Math.floor(PaperEnum.FULL_MARK.value() / clientNodeCount));

        Long totalScore = score * clientNodeCount;
        ruleList.stream().forEach(item -> {
            if (item.getNodeType().equals(SceneRuleEnum.SERVICE_NODE_TYPE.value())) {
                item.setScore(score);
            }
        });

        TPartQuestion tPartQuestion = new TPartQuestion();
        tPartQuestion.setProcessRules(JSON.toJSONString(ruleList));
        tPartQuestion.setNeedReset(true);
        tPartQuestion.setScore(totalScore.intValue());



        List<Integer> paperPartIdList = partQuestionList.stream().map(item -> item.getPaperPartId()).collect(Collectors.toList());

        List<TPaper> paperList = tPaperMapper.findAllByPartIdList(paperPartIdList);
        paperList.forEach(item -> item.setNeedReset(true));

        tPaperService.updateBatchById(paperList);
        tPartQuestionService.update(tPartQuestion, queryWrapper);
    }
}
