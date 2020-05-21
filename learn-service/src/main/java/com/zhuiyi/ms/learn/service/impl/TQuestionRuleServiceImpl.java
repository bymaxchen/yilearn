package com.zhuiyi.ms.learn.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhuiyi.ms.learn.common.Constants;
import com.zhuiyi.ms.learn.entity.TBaseQuestion;
import com.zhuiyi.ms.learn.entity.TEasyQuestion;
import com.zhuiyi.ms.learn.entity.TQuestionRule;
import com.zhuiyi.ms.learn.entity.bo.QuestionRuleCountBO;
import com.zhuiyi.ms.learn.entity.bo.ResponsePage;
import com.zhuiyi.ms.learn.entity.vo.req.QuestionRuleListReqVO;
import com.zhuiyi.ms.learn.entity.vo.req.QuestionRuleReqVO;
import com.zhuiyi.ms.learn.entity.vo.res.RuleBaseQuestionResVO;
import com.zhuiyi.ms.learn.mapper.TQuestionRuleMapper;
import com.zhuiyi.ms.learn.service.api.ITQuestionRuleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhuiyi.ms.learn.util.PageBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
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
public class TQuestionRuleServiceImpl extends ServiceImpl<TQuestionRuleMapper, TQuestionRule> implements ITQuestionRuleService {
    @Resource
    private TQuestionRuleCategoryServiceImpl tQuestionRuleCategoryService;

    @Resource
    private TQuestionRuleMapper tQuestionRuleMapper;

    @Resource
    private TBaseQuestionServiceImpl tBaseQuestionService;

    @Resource
    private TEasyQuestionServiceImpl tEasyQuestionService;

    @Override
    public Boolean updateNullCategoryId() {
        TQuestionRule tQuestionRule = new TQuestionRule();
        tQuestionRule.setQuestionRuleCategoryId(Constants.UNKNOWN_CATEGORY_ID);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.isNull("question_rule_category_id");
        return this.update(tQuestionRule, queryWrapper);
    }

    @Override
    public ResponsePage findAll(QuestionRuleListReqVO questionRuleListReqVO) {
        QueryWrapper queryWrapper = new QueryWrapper();

        String searchText = questionRuleListReqVO.getSearchText();

        if (StringUtils.isNotEmpty(searchText)) {
            if (StringUtils.isNumeric(searchText)) {
                queryWrapper.eq("question_rule_id", Integer.parseInt(searchText));
                queryWrapper.or();
                queryWrapper.like("question_rule_name", searchText);
            } else {
                queryWrapper.like("question_rule_name", searchText);
            }
        }

        if (questionRuleListReqVO.getQuestionRuleCategoryId() != null) {
            List<Integer> categoryIdList = tQuestionRuleCategoryService.findIdListByRootId(questionRuleListReqVO.getQuestionRuleCategoryId());
            queryWrapper.in("question_rule_category_id", categoryIdList);
        }

        IPage<TQuestionRule> ipage;

        queryWrapper.orderByDesc("update_time");

        if (questionRuleListReqVO.getPage() != null) {
            ipage = this.page(questionRuleListReqVO.getPage(), queryWrapper);
        } else {
            ipage = this.page(PageBuilder.getMaxPage(), queryWrapper);
        }




        List<Integer> idList = ipage.getRecords().stream().map(item -> item.getQuestionRuleId()).collect(Collectors.toList());

        if(!idList.isEmpty()) {
            List<QuestionRuleCountBO> questionRuleCountBOList = tQuestionRuleMapper.findAllByQuestionRuleIdList(idList);

            for(TQuestionRule tQuestionRule: ipage.getRecords()) {
                for(QuestionRuleCountBO questionRuleCountBO: questionRuleCountBOList) {
                    if (tQuestionRule.getQuestionRuleId() == questionRuleCountBO.getQuestionRuleId()) {
                        tQuestionRule.setRelatedQuestionCount(questionRuleCountBO.getRelatedQuestionCount());
                    }
                }
            }
        }

        return PageBuilder.buildResponsePage(ipage);
    }

    @Override
    public Boolean deleteById(Integer id) {
        return this.removeById(id);
    }

    @Override
    public Boolean updateById(Integer id, QuestionRuleReqVO questionRuleReqVO) {
        TQuestionRule tQuestionRule = new TQuestionRule();
        BeanUtils.copyProperties(questionRuleReqVO, tQuestionRule);
        tQuestionRule.setQuestionRuleId(id);
        return this.updateById(tQuestionRule);
    }

    @Override
    public TQuestionRule create(QuestionRuleReqVO questionRuleReqVO) {
        TQuestionRule tQuestionRule = new TQuestionRule();
        BeanUtils.copyProperties(questionRuleReqVO, tQuestionRule);
        this.save(tQuestionRule);
        return tQuestionRule;
    }

    @Override
    public Collection<TQuestionRule> findAllDetail(QuestionRuleListReqVO questionRuleListReqVO) {
        if (questionRuleListReqVO.getRuleIdList() != null && !questionRuleListReqVO.getRuleIdList().isEmpty()) {
            return this.listByIds(questionRuleListReqVO.getRuleIdList());
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<RuleBaseQuestionResVO> findAllQuestions(Integer ruleId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("question_rule_id", ruleId);
        List<TEasyQuestion> easyQuestionList = tEasyQuestionService.list(queryWrapper);

        if (easyQuestionList.isEmpty()) {
            return new ArrayList<>();
        }

        List<Integer> idList = easyQuestionList.stream().map(item -> item.getEasyQuestionId()).collect(Collectors.toList());
        Collection<TBaseQuestion> baseQuestionList = tBaseQuestionService.listByIds(idList);

        List<RuleBaseQuestionResVO> ruleBaseQuestionResVOList = baseQuestionList.stream().map(item -> {
            RuleBaseQuestionResVO ruleBaseQuestionResVO = new RuleBaseQuestionResVO();
            ruleBaseQuestionResVO.setBaseQuestionId(item.getBaseQuestionId());
            ruleBaseQuestionResVO.setQuestionName(item.getQuestionName());
            return ruleBaseQuestionResVO;
        }).collect(Collectors.toList());

        return ruleBaseQuestionResVOList;
    }
}
