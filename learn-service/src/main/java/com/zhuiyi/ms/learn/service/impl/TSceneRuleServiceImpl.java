package com.zhuiyi.ms.learn.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhuiyi.ms.learn.common.enums.SceneRuleEnum;
import com.zhuiyi.ms.learn.entity.TBaseQuestion;
import com.zhuiyi.ms.learn.entity.TSceneQuestion;
import com.zhuiyi.ms.learn.entity.TSceneRule;
import com.zhuiyi.ms.learn.entity.bo.RuleBO;
import com.zhuiyi.ms.learn.entity.bo.ResponsePage;
import com.zhuiyi.ms.learn.entity.vo.req.SceneRuleListReqVO;
import com.zhuiyi.ms.learn.entity.vo.req.SceneRuleReqVO;
import com.zhuiyi.ms.learn.entity.vo.res.RuleBaseQuestionResVO;
import com.zhuiyi.ms.learn.mapper.TSceneRuleMapper;
import com.zhuiyi.ms.learn.service.api.ITSceneRuleService;
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
 * @since 2019-10-22
 */
@Service
@DS("#session.dataSourceName")
public class TSceneRuleServiceImpl extends ServiceImpl<TSceneRuleMapper, TSceneRule> implements ITSceneRuleService {
    @Resource
    private TSceneRuleCategoryServiceImpl tSceneRuleCategoryService;

    @Resource
    private TSceneQuestionServiceImpl tSceneQuestionService;

    @Resource
    private TBaseQuestionServiceImpl tBaseQuestionService;

    @Override
    public ResponsePage findAll(SceneRuleListReqVO sceneRuleListReqVO) {
        QueryWrapper queryWrapper = new QueryWrapper();

        String searchText = sceneRuleListReqVO.getSearchText();

        if (StringUtils.isNotEmpty(searchText)) {
            if (StringUtils.isNumeric(searchText)) {
                queryWrapper.eq("scene_rule_id", Integer.parseInt(searchText));
                queryWrapper.or();
                queryWrapper.like("scene_rule_name", searchText);
            } else {
                queryWrapper.like("scene_rule_name", searchText);
            }
        }

        if (sceneRuleListReqVO.getSceneRuleCategoryId() != null) {
            List<Integer> categoryIdList = tSceneRuleCategoryService.findIdListByRootId(sceneRuleListReqVO.getSceneRuleCategoryId());
            queryWrapper.in("scene_rule_category_id", categoryIdList);
        }

        queryWrapper.orderByDesc("update_time");

        IPage<TSceneRule> ipage;
        if (sceneRuleListReqVO.getPage() != null) {
            ipage = this.page(sceneRuleListReqVO.getPage(), queryWrapper);
        } else {
            ipage = this.page(PageBuilder.getMaxPage(), queryWrapper);
        }



        List<TSceneQuestion> tSceneQuestionList = tSceneQuestionService.list();

        for(TSceneRule tSceneRule: ipage.getRecords()) {
            Integer count = 0;

            for(TSceneQuestion tSceneQuestion: tSceneQuestionList) {

                String rulesStr;
                try {
                    if (tSceneRule.getRuleType() == SceneRuleEnum.NODE_PROCESS_RULE.value()) {
                        rulesStr = tSceneQuestion.getProcessRules();
                        List<RuleBO> RuleBOList = JSON.parseArray(rulesStr, RuleBO.class);
                        for(RuleBO rule: RuleBOList) {
                            if(!rule.getNodeType().equals(1) && rule.getRuleId().equals(tSceneRule.getSceneRuleId())) {
                                count++;
                                break;
                            }
                        }
                    } else {
                        rulesStr = tSceneQuestion.getGlobalRules();
                        List<Integer> globalRuleIdLIst = JSON.parseArray(rulesStr, Integer.class);
                        for(Integer ruleId: globalRuleIdLIst) {
                            if(ruleId.equals(tSceneRule.getSceneRuleId())) {
                                count++;
                                break;
                            }
                        }
                    }
                } catch (Exception e) {
                    continue;
                }

            }
            tSceneRule.setRelatedQuestionCount(count);
        }

        return PageBuilder.buildResponsePage(ipage);
    }

    @Override
    public List<RuleBaseQuestionResVO> findAllQuestions(Integer ruleId) {

        TSceneRule tSceneRule = this.getById(ruleId);

        List<TSceneQuestion> tSceneQuestionList = tSceneQuestionService.list();

        List<Integer> questionIdList = new ArrayList<>();
        for(TSceneQuestion tSceneQuestion: tSceneQuestionList) {
            String rulesStr;

            try {
                if (tSceneRule.getRuleType() == SceneRuleEnum.NODE_PROCESS_RULE.value()) {
                    rulesStr = tSceneQuestion.getProcessRules();
                    List<RuleBO> ruleBOList = JSON.parseArray(rulesStr, RuleBO.class);
                    for(RuleBO rule: ruleBOList) {
                        if (!rule.getNodeType().equals(1) && rule.getRuleId().equals(tSceneRule.getSceneRuleId())) {
                            questionIdList.add(tSceneQuestion.getSceneQuestionId());
                        }
                    }
                } else {
                    rulesStr = tSceneQuestion.getGlobalRules();
                    List<Integer> globalRuleIdLIst = JSON.parseArray(rulesStr, Integer.class);
                    for(Integer rule: globalRuleIdLIst) {
                        if(rule.equals(tSceneRule.getSceneRuleId())) {
                            questionIdList.add(tSceneQuestion.getSceneQuestionId());
                        }
                    }
                }
            } catch (Exception e) {
                continue;
            }
        }

        if (questionIdList.isEmpty()) {
            return new ArrayList<>();
        }

        Collection<TBaseQuestion> baseQuestionList = tBaseQuestionService.listByIds(questionIdList);
        List<RuleBaseQuestionResVO> ruleBaseQuestionResVOList = baseQuestionList.stream().map(item -> {
            RuleBaseQuestionResVO ruleBaseQuestionResVO = new RuleBaseQuestionResVO();
            ruleBaseQuestionResVO.setBaseQuestionId(item.getBaseQuestionId());
            ruleBaseQuestionResVO.setQuestionName(item.getQuestionName());
            return ruleBaseQuestionResVO;
        }).collect(Collectors.toList());

        return ruleBaseQuestionResVOList;
    }

    @Override
    public TSceneRule create(SceneRuleReqVO sceneRuleReqVO) {
        TSceneRule tSceneRule = new TSceneRule();
        BeanUtils.copyProperties(sceneRuleReqVO, tSceneRule);
        this.save(tSceneRule);
        return tSceneRule;
    }

    @Override
    public Boolean updateById(Integer id, SceneRuleReqVO sceneRuleReqVO) {
        TSceneRule tSceneRule = new TSceneRule();
        BeanUtils.copyProperties(sceneRuleReqVO, tSceneRule);
        tSceneRule.setSceneRuleId(id);
        return this.updateById(tSceneRule);
    }

    @Override
    public Boolean deleteById(Integer id) {
        return this.removeById(id);
    }

    @Override
    public Boolean updateNullCategoryId(Integer type) {
        TSceneRule tSceneRule = new TSceneRule();

        if (type == SceneRuleEnum.PROCESS_RULE.value()) {
            tSceneRule.setSceneRuleCategoryId(SceneRuleEnum.PROCESS_RULE_UNKNOWN_CATEGORY_ID.value());
        } else {
            tSceneRule.setSceneRuleCategoryId(SceneRuleEnum.GLOBAL_RULE_UNKNOWN_CATEGORY_ID.value());
        }


        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.isNull("scene_rule_category_id");
        return this.update(tSceneRule, queryWrapper);
    }

    @Override
    public Collection<TSceneRule> findAllDetail(SceneRuleListReqVO sceneRuleListReqVO) {
        if (sceneRuleListReqVO.getRuleIdList() != null && !sceneRuleListReqVO.getRuleIdList().isEmpty()) {
            return this.listByIds(sceneRuleListReqVO.getRuleIdList());
        } else {
            return new ArrayList<>();
        }
    }
}
