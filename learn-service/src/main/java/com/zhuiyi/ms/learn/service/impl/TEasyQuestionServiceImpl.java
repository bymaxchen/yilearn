package com.zhuiyi.ms.learn.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhuiyi.ms.learn.common.enums.DeleteStatus;
import com.zhuiyi.ms.learn.common.enums.QuestionsEnum;
import com.zhuiyi.ms.learn.entity.TBaseQuestion;
import com.zhuiyi.ms.learn.entity.TEasyQuestion;
import com.zhuiyi.ms.learn.entity.TQuestionRule;
import com.zhuiyi.ms.learn.entity.vo.req.EasyQuestionReqVO;
import com.zhuiyi.ms.learn.entity.vo.res.EasyQuestionResVO;
import com.zhuiyi.ms.learn.mapper.TEasyQuestionMapper;
import com.zhuiyi.ms.learn.service.api.ITEasyQuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bymax
 * @since 2019-10-15
 */
@Service
@DS("#session.dataSourceName")
public class TEasyQuestionServiceImpl extends ServiceImpl<TEasyQuestionMapper, TEasyQuestion> implements ITEasyQuestionService {
    @Resource
    private TBaseQuestionServiceImpl tBaseQuestionService;

    @Resource
    private TQuestionRuleServiceImpl tQuestionRuleService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TEasyQuestion create(EasyQuestionReqVO easyQuestionReqVO) {
        TBaseQuestion tBaseQuestion = new TBaseQuestion();
        BeanUtils.copyProperties(easyQuestionReqVO, tBaseQuestion);

        tBaseQuestion.setType(QuestionsEnum.EASY_QUESTION.value());
        tBaseQuestionService.save(tBaseQuestion);

        TEasyQuestion tEasyQuestion = new TEasyQuestion();
        BeanUtils.copyProperties(easyQuestionReqVO, tEasyQuestion);
        tEasyQuestion.setEasyQuestionId(tBaseQuestion.getBaseQuestionId());
        this.save(tEasyQuestion);

        return tEasyQuestion;
    }

    @Override
    public EasyQuestionResVO findOne(Integer id) {
        TBaseQuestion tBaseQuestion = tBaseQuestionService.getById(id);

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("easy_question_id", id);

        TEasyQuestion tEasyQuestion = this.getOne(queryWrapper);

        TQuestionRule tQuestionRule = tQuestionRuleService.getById(tEasyQuestion.getQuestionRuleId());

        EasyQuestionResVO easyQuestionResVO = new EasyQuestionResVO();

        BeanUtils.copyProperties(tBaseQuestion, easyQuestionResVO);
        BeanUtils.copyProperties(tEasyQuestion, easyQuestionResVO);

        if (tQuestionRule != null) {
            easyQuestionResVO.setQuestionRuleId(tQuestionRule.getQuestionRuleId());
            easyQuestionResVO.setQuestionRuleCategoryId(tQuestionRule.getQuestionRuleCategoryId());
        }

        return easyQuestionResVO;
    }

    @Override
    public Boolean updateById(Integer id, EasyQuestionReqVO easyQuestionReqVO) {
        TBaseQuestion tBaseQuestion = new TBaseQuestion();
        BeanUtils.copyProperties(easyQuestionReqVO, tBaseQuestion);
        tBaseQuestion.setBaseQuestionId(id);

        tBaseQuestionService.updateById(tBaseQuestion);

        TEasyQuestion tEasyQuestion = new TEasyQuestion();
        BeanUtils.copyProperties(easyQuestionReqVO, tEasyQuestion);
        tEasyQuestion.setEasyQuestionId(id);

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("easy_question_id", id);

        return this.update(tEasyQuestion, queryWrapper);
    }
}
