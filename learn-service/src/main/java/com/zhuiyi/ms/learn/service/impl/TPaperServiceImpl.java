package com.zhuiyi.ms.learn.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhuiyi.ms.learn.common.Constants;
import com.zhuiyi.ms.learn.common.enums.QuestionsEnum;
import com.zhuiyi.ms.learn.entity.*;
import com.zhuiyi.ms.learn.entity.bo.ResponsePage;
import com.zhuiyi.ms.learn.entity.bo.RuleBO;
import com.zhuiyi.ms.learn.entity.vo.req.PaperListReqVO;
import com.zhuiyi.ms.learn.entity.vo.req.PaperPartReqVO;
import com.zhuiyi.ms.learn.entity.vo.req.PaperReqVO;
import com.zhuiyi.ms.learn.entity.vo.res.PaperPartResVO;
import com.zhuiyi.ms.learn.entity.vo.res.PaperQuestionResVO;
import com.zhuiyi.ms.learn.entity.vo.res.PaperResVO;
import com.zhuiyi.ms.learn.mapper.TPaperMapper;
import com.zhuiyi.ms.learn.service.api.ITPaperService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhuiyi.ms.learn.util.PageBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.util.*;
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
public class TPaperServiceImpl extends ServiceImpl<TPaperMapper, TPaper> implements ITPaperService {
    @Resource
    private TPaperPartServiceImpl tPaperPartService;

    @Resource
    private TPartQuestionServiceImpl tPartQuestionService;

    @Resource
    private TEasyQuestionServiceImpl tEasyQuestionService;

    @Resource
    private TSceneQuestionServiceImpl tSceneQuestionService;

    @Resource
    private TBaseQuestionServiceImpl tBaseQuestionService;

    @Resource
    private TPaperCategoryServiceImpl tPaperCategoryService;

    @Resource
    private TPaperMapper tPaperMapper;

    @Resource
    private TSceneRuleServiceImpl tSceneRuleService;

    @Override
    public ResponsePage findAll(PaperListReqVO paperListReqVO) {
        Integer paperId = null;
        String searchText = paperListReqVO.getSearchText();
        if (StringUtils.isNotEmpty(searchText)) {
            if (StringUtils.isNumeric(searchText)) {
                paperId = Integer.parseInt(searchText);
            }
        }

        List<Integer> categoryIdList = new ArrayList<>();
        Collection<TPaperCategory> categoryList = tPaperCategoryService.list();
        HashMap<Integer, String> categoryId2Name = new HashMap<>();

        for(TPaperCategory tPaperCategory: categoryList) {
            categoryId2Name.put(tPaperCategory.getPaperCategoryId(), tPaperCategory.getPaperCategoryName());
        }

        if (paperListReqVO.getPaperCategoryId() != null) {
            categoryIdList = tPaperCategoryService.findIdListByRootId(paperListReqVO.getPaperCategoryId());
        }

        Page page = paperListReqVO.getPage();
        if (page == null) {
            page = PageBuilder.getMaxPage();
        }

        IPage<TPaper> iPage = tPaperMapper.findAll(page, paperListReqVO.getType(), categoryIdList, paperListReqVO.getLevel(), paperId, searchText);

        List<TPaper> paperList = iPage.getRecords();

        for(TPaper tPaper: paperList) {
            tPaper.setPaperCategoryName(categoryId2Name.get(tPaper.getPaperCategoryId()));
        }

        return PageBuilder.buildResponsePage(iPage);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TPaper create(PaperReqVO paperReqVO) {
        TPaper tPaper = new TPaper();
        BeanUtils.copyProperties(paperReqVO, tPaper);
        this.save(tPaper);
        this.createQuestionPart(tPaper.getPaperId(), paperReqVO);

        return tPaper;
    }

    public void createQuestionPart(Integer paperId, PaperReqVO paperReqVO) {
        List<List<TPartQuestion>> partQuestionList = new ArrayList<>();
        List<TPaperPart> paperPartList = new ArrayList<>();

        int index = 0;
        for(PaperPartReqVO paperPartReqVO: paperReqVO.getPartList()) {
            TPaperPart tPaperPart = new TPaperPart();
            BeanUtils.copyProperties(paperPartReqVO, tPaperPart);
            tPaperPart.setPosition(index++);
            tPaperPart.setPaperId(paperId);
            paperPartList.add(tPaperPart);
            partQuestionList.add(paperPartReqVO.getQuestionList());
        }

        tPaperPartService.saveBatch(paperPartList);

        List<TPartQuestion> questionList = new ArrayList<>();
        for(int i = 0 ; i < paperPartList.size(); i++) {
            int j = 0;
            for(TPartQuestion tPartQuestion: partQuestionList.get(i)) {
                tPartQuestion.setPaperPartId(paperPartList.get(i).getPaperPartId());
                tPartQuestion.setPosition(j++);
                questionList.add(tPartQuestion);
            }
        }

        tPartQuestionService.saveBatch(questionList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean updateOne(Integer id, PaperReqVO paperReqVO) {
        TPaper tPaper = new TPaper();
        BeanUtils.copyProperties(paperReqVO, tPaper);
        tPaper.setPaperId(id);

        // 试卷保存完毕后，默认不需要重置试卷内的评分细则
        tPaper.setNeedReset(false);

        this.updateById(tPaper);

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("paper_id", id);
        tPaperPartService.remove(queryWrapper);
        this.createQuestionPart(id, paperReqVO);
        return true;
    }

    @Override
    public PaperResVO findOne(Integer id) {
        TPaper tPaper = this.getById(id);

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("paper_id", id);

        List<TPaperPart> tPaperPartList = tPaperPartService.list(queryWrapper);

        List<Integer> paperPartIdList = tPaperPartList.stream().map(item -> item.getPaperPartId()).collect(Collectors.toList());

        QueryWrapper queryWrapper1 = new QueryWrapper();

        PaperResVO paperResVO = new PaperResVO();
        BeanUtils.copyProperties(tPaper, paperResVO);

        List<TPartQuestion> tPartQuestionList = new ArrayList<>();
        if (!paperPartIdList.isEmpty()) {
            queryWrapper1.in("paper_part_id", paperPartIdList);
            queryWrapper1.orderByAsc("position");
            tPartQuestionList = tPartQuestionService.list(queryWrapper1);
        } else {
            return paperResVO;
        }



        HashMap<Integer, TPartQuestion> questionId2PartQuestion = new HashMap<>();
        for(TPartQuestion tPartQuestion: tPartQuestionList) {
            questionId2PartQuestion.put(tPartQuestion.getPartQuestionId(), tPartQuestion);
        }

        List<Integer> questionIdList = tPartQuestionList.stream().map(item -> item.getPartQuestionId()).collect(Collectors.toList());

        List<Integer> easyQuestionIdList = new ArrayList<>();
        List<Integer> sceneQuestionIdList = new ArrayList<>();


        HashMap<Integer, List<Integer>> partId2QuestionIdList = new HashMap<>();

        for(TPaperPart tPaperPart: tPaperPartList) {
            for(TPartQuestion tPartQuestion: tPartQuestionList) {
                if (tPartQuestion.getPaperPartId().equals(tPaperPart.getPaperPartId())) {
                    List<Integer> partQuestionIdList = partId2QuestionIdList.getOrDefault(tPaperPart.getPaperPartId(), new ArrayList<>());
                    partQuestionIdList.add(tPartQuestion.getPartQuestionId());
                    partId2QuestionIdList.put(tPaperPart.getPaperPartId(), partQuestionIdList);

                    if (tPaperPart.getType() == QuestionsEnum.EASY_QUESTION.value()) {
                        easyQuestionIdList.add(tPartQuestion.getPartQuestionId());
                    } else {
                        sceneQuestionIdList.add(tPartQuestion.getPartQuestionId());
                    }
                }
            }
        }

        QueryWrapper queryWrapper2 = new QueryWrapper();
        QueryWrapper queryWrapper3 = new QueryWrapper();
        QueryWrapper queryWrapper4 = new QueryWrapper();

        List<TEasyQuestion> tEasyQuestionList = new ArrayList<>();

        if (!easyQuestionIdList.isEmpty()) {
            queryWrapper2.in("easy_question_id", easyQuestionIdList);
            tEasyQuestionList = tEasyQuestionService.list(queryWrapper2);
        }

        List<TSceneQuestion> tSceneQuestionList = new ArrayList<>();

        if (!sceneQuestionIdList.isEmpty()) {
            queryWrapper3.in("scene_question_id", sceneQuestionIdList);
            tSceneQuestionList = tSceneQuestionService.list(queryWrapper3);
        }

        List<TBaseQuestion> tBaseQuestionList = new ArrayList<>();

        if (!questionIdList.isEmpty()) {
            queryWrapper4.in("base_question_id", questionIdList);
            tBaseQuestionList = tBaseQuestionService.list(queryWrapper4);
        }


        HashMap<Integer, TBaseQuestion> questionId2BaseQuestion = new HashMap<>();
        for(TBaseQuestion tBaseQuestion: tBaseQuestionList) {
            questionId2BaseQuestion.put(tBaseQuestion.getBaseQuestionId(), tBaseQuestion);
        }


        List<PaperPartResVO> partList = new ArrayList<>();

        List<TSceneRule> sceneRuleList = tSceneRuleService.list();
        HashMap<Integer, String> ruleId2NameMap = new HashMap<>();

        for(TSceneRule tSceneRule: sceneRuleList) {
            ruleId2NameMap.put(tSceneRule.getSceneRuleId(), tSceneRule.getSceneRuleName());
        }


        for(TPaperPart tPaperPart: tPaperPartList) {
            PaperPartResVO paperPartResVO = new PaperPartResVO();
            BeanUtils.copyProperties(tPaperPart, paperPartResVO);

            List<PaperQuestionResVO> questionList = new ArrayList<>();

            // 问答题
            if (tPaperPart.getType() == QuestionsEnum.EASY_QUESTION.value()) {
                for(TEasyQuestion tEasyQuestion: tEasyQuestionList) {
                    if (partId2QuestionIdList.get(tPaperPart.getPaperPartId()).contains(tEasyQuestion.getEasyQuestionId())) {
                        PaperQuestionResVO paperQuestionResVO = new PaperQuestionResVO();
                        BeanUtils.copyProperties(tEasyQuestion, paperQuestionResVO);

                        paperQuestionResVO.setIsRequired(questionId2PartQuestion.get(tEasyQuestion.getEasyQuestionId()).getIsRequired());
                        paperQuestionResVO.setScore(questionId2PartQuestion.get(tEasyQuestion.getEasyQuestionId()).getScore());

                        BeanUtils.copyProperties(questionId2BaseQuestion.get(tEasyQuestion.getEasyQuestionId()), paperQuestionResVO);

                        questionList.add(paperQuestionResVO);
                    }
                }
            } else { // 情景题
                for(TSceneQuestion tSceneQuestion: tSceneQuestionList) {
                    if (partId2QuestionIdList.get(tPaperPart.getPaperPartId()).contains(tSceneQuestion.getSceneQuestionId())) {
                        PaperQuestionResVO paperQuestionResVO = new PaperQuestionResVO();
                        BeanUtils.copyProperties(tSceneQuestion, paperQuestionResVO);
                        BeanUtils.copyProperties(questionId2BaseQuestion.get(tSceneQuestion.getSceneQuestionId()), paperQuestionResVO);
                        paperQuestionResVO.setIsRequired(questionId2PartQuestion.get(tSceneQuestion.getSceneQuestionId()).getIsRequired());
                        paperQuestionResVO.setScore(questionId2PartQuestion.get(tSceneQuestion.getSceneQuestionId()).getScore());
                        paperQuestionResVO.setNeedReset(questionId2PartQuestion.get(tSceneQuestion.getSceneQuestionId()).getNeedReset());
                        paperQuestionResVO.setProcessRules(this.generateProcessRules(tSceneQuestion.getProcessRules(),
                                questionId2PartQuestion.get(tSceneQuestion.getSceneQuestionId()).getProcessRules(), ruleId2NameMap));

                        questionList.add(paperQuestionResVO);
                    }
                }
            }

            // 根据问题在试卷里面的位置进行排序
            questionList.sort((PaperQuestionResVO o1, PaperQuestionResVO o2) ->
                    questionId2PartQuestion.get(o1.getBaseQuestionId()).getPosition()  - questionId2PartQuestion.get(o2.getBaseQuestionId()).getPosition());

            paperPartResVO.setQuestionList(questionList);

            partList.add(paperPartResVO);
        }

        paperResVO.setPartList(partList);

        return paperResVO;
    }

    /**
     * 根据试卷里面情景题的得分规则，给原来情景题的processRules加上对应的score
     * @param processRules
     * @param scoreList
     * @return
     */
    public String generateProcessRules(String processRules, String scoreList, HashMap<Integer, String> ruleId2NameMap) {
        List<RuleBO> ruleBOList;
        try {
            ruleBOList = JSON.parseArray(scoreList, RuleBO.class);
        } catch (Exception e) {
            return "[]";
        }

        JSONArray processRuleList = JSON.parseArray(processRules);


        for(int i = 0 ; i < processRuleList.size() ; i++) {
            JSONObject processRule = processRuleList.getJSONObject(i);
            Long score = ruleBOList.get(i).getScore();
            if (score != null) {
                processRule.put("score", score);
            } else {
                processRule.put("score", 0);
            }

            // 更新规则名称
            processRule.put("ruleName", ruleId2NameMap.getOrDefault(processRule.getInteger("ruleId"), ""));
        }


        return JSON.toJSONString(processRuleList);
    }

    @Override
    public Boolean updateNullCategoryId() {
        TPaper tPaper = new TPaper();
        tPaper.setPaperCategoryId(Constants.UNKNOWN_CATEGORY_ID);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.isNull("paper_category_id");
        return this.update(tPaper, queryWrapper);
    }

    @Override
    public List<TPaper> findAllByQuestionId(Integer id) {
        return tPaperMapper.findAllByQuestionId(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean RemoveResetFlag(Integer id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("paper_id", id);
        List<TPaperPart> tPaperPartList = tPaperPartService.list(queryWrapper);
        List<Integer> tPaperPartIdList = tPaperPartList.stream().map(item -> item.getPaperPartId()).collect(Collectors.toList());

        TPartQuestion tPartQuestion = new TPartQuestion();
        tPartQuestion.setNeedReset(false);

        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.in("paper_part_id", tPaperPartIdList);


        TPaper tPaper = new TPaper();
        tPaper.setNeedReset(false);
        tPaper.setPaperId(id);

        this.updateById(tPaper);
        return tPartQuestionService.update(tPartQuestion, updateWrapper);
    }
}
