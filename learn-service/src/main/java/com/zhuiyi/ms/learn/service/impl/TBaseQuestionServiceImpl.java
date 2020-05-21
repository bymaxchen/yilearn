package com.zhuiyi.ms.learn.service.impl;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhuiyi.ms.learn.common.Constants;
import com.zhuiyi.ms.learn.common.enums.QuestionsEnum;
import com.zhuiyi.ms.learn.entity.TBaseQuestion;
import com.zhuiyi.ms.learn.entity.TQuestionCategory;
import com.zhuiyi.ms.learn.entity.TSceneQuestion;
import com.zhuiyi.ms.learn.entity.bo.ResponsePage;
import com.zhuiyi.ms.learn.entity.vo.req.QuestionListReqVO;
import com.zhuiyi.ms.learn.entity.vo.res.SceneQuestionResVO;
import com.zhuiyi.ms.learn.mapper.TBaseQuestionMapper;
import com.zhuiyi.ms.learn.service.api.ITBaseQuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhuiyi.ms.learn.util.PageBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bymaxc
 * @since 2019-10-15
 */
@Service
@DS("#session.dataSourceName")
public class TBaseQuestionServiceImpl extends ServiceImpl<TBaseQuestionMapper, TBaseQuestion> implements ITBaseQuestionService {
    @Resource
    private TQuestionCategoryServiceImpl tQuestionCategoryService;

    @Resource
    private TSceneQuestionServiceImpl tSceneQuestionService;

    @Resource
    private TBaseQuestionMapper tBaseQuestionMapper;


    /**
     *  如果是拉取情景题(type = 1)，则需要把情景题详情也返回
     * @param questionListReqVO
     * @return
     */

    @Override
    public ResponsePage findAll(QuestionListReqVO questionListReqVO) {
        String searchText = questionListReqVO.getSearchText();

        Integer baseQuestionId = null;
        if (StringUtils.isNotEmpty(searchText)) {
            if (StringUtils.isNumeric(searchText)) {
                baseQuestionId = Integer.parseInt(searchText);
            }
        }

        List<Integer> categoryIdList = new ArrayList<>();
        if (questionListReqVO.getQuestionCategoryId() != null) {
            categoryIdList = tQuestionCategoryService.findIdListByRootId(questionListReqVO.getQuestionCategoryId());
        }

        Page page = questionListReqVO.getPage();
        if (page == null) {
            page = PageBuilder.getMaxPage();
        }

        if (questionListReqVO.getType() == QuestionsEnum.EASY_QUESTION.value()) {
            return PageBuilder.buildResponsePage(tBaseQuestionMapper.findAll(page, questionListReqVO.getType(), categoryIdList, questionListReqVO.getLevel(), questionListReqVO.getLevelList(), baseQuestionId, searchText));
        }

        IPage<TBaseQuestion> iPage = tBaseQuestionMapper.findAll(page, questionListReqVO.getType(), categoryIdList, questionListReqVO.getLevel(), questionListReqVO.getLevelList(), baseQuestionId, searchText);

        List<TBaseQuestion> tBaseQuestionList = iPage.getRecords();
        Long count = iPage.getTotal();

        if (tBaseQuestionList.isEmpty()) {
            return PageBuilder.buildEmptyResponsePage();
        }

        List<Integer> sceneQuestionIdList = tBaseQuestionList.stream().map(item -> item.getBaseQuestionId()).collect(Collectors.toList());

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.in("scene_question_id", sceneQuestionIdList);
        List<TSceneQuestion> tSceneQuestionList = tSceneQuestionService.list(queryWrapper);

        List<SceneQuestionResVO> sceneQuestionResVOList = new ArrayList<>();

        for(TBaseQuestion tBaseQuestion: tBaseQuestionList) {
            SceneQuestionResVO sceneQuestionResVO = new SceneQuestionResVO();
            BeanUtils.copyProperties(tBaseQuestion, sceneQuestionResVO);
            for(TSceneQuestion tSceneQuestion: tSceneQuestionList) {
                if (tBaseQuestion.getBaseQuestionId().equals(tSceneQuestion.getSceneQuestionId())) {
                    BeanUtils.copyProperties(tSceneQuestion, sceneQuestionResVO);
                    break;
                }
            }
            sceneQuestionResVOList.add(sceneQuestionResVO);
        }

        return PageBuilder.buildResponsePage(sceneQuestionResVOList, count);
    }

    @Override
    public Boolean updateNullCategoryId(Integer type) {
        TBaseQuestion tBaseQuestion = new TBaseQuestion();

        if (type == QuestionsEnum.EASY_QUESTION.value()) {
            tBaseQuestion.setQuestionCategoryId(QuestionsEnum.EASY_QUESTION_RULE_UNKNOWN_CATEGORY_ID.value());
        } else {
            tBaseQuestion.setQuestionCategoryId(QuestionsEnum.SCENE_QUESTION_RULE_UNKNOWN_CATEGORY_ID.value());
        }

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.isNull("question_category_id");
        return this.update(tBaseQuestion, queryWrapper);
    }
}
