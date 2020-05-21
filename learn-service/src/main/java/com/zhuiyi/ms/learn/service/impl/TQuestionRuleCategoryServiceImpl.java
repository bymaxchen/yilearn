package com.zhuiyi.ms.learn.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhuiyi.ms.learn.common.Constants;
import com.zhuiyi.ms.learn.entity.TQuestionRuleCategory;
import com.zhuiyi.ms.learn.entity.bo.QuestionRuleCategoryTreeNodeBO;
import com.zhuiyi.ms.learn.entity.vo.req.QuestionCategoryReqVO;
import com.zhuiyi.ms.learn.entity.vo.req.QuestionRuleCategoryReqVO;
import com.zhuiyi.ms.learn.mapper.TQuestionRuleCategoryMapper;
import com.zhuiyi.ms.learn.service.api.ITQuestionRuleCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhuiyi.ms.learn.util.NestSetUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
public class TQuestionRuleCategoryServiceImpl extends ServiceImpl<TQuestionRuleCategoryMapper, TQuestionRuleCategory> implements ITQuestionRuleCategoryService {
    @Resource
    private TQuestionRuleCategoryMapper tQuestionRuleCategoryMapper;

    @Resource
    private TQuestionRuleServiceImpl tQuestionRuleService;

    @Override
    public List<QuestionRuleCategoryTreeNodeBO> findAll() {
        QuestionRuleCategoryTreeNodeBO questionCategoryTreeNodeBO = new QuestionRuleCategoryTreeNodeBO();

        return questionCategoryTreeNodeBO.getTree(tQuestionRuleCategoryMapper.findAllWithDepth(), tQuestionRuleCategoryMapper.findAllWithQuestionCount());
    }

    @Override
    public List<Integer> findIdListByRootId(Integer rootId) {
        TQuestionRuleCategory tQuestionRuleCategory = this.getById(rootId);

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.gt("lft", tQuestionRuleCategory.getLft());
        queryWrapper.lt("rgt", tQuestionRuleCategory.getRgt());

        List<TQuestionRuleCategory> tQuestionCategoryList = this.list(queryWrapper);

        List<Integer> idList = new ArrayList<>(Arrays.asList(rootId));
        for(TQuestionRuleCategory item : tQuestionCategoryList) {
            idList.add(item.getQuestionRuleCategoryId());
        }
        return idList;
    }

    @Override
    public Boolean updateById(Integer questionRuleCategoryId, QuestionRuleCategoryReqVO questionRuleCategoryReqVO) {
        TQuestionRuleCategory tQuestionRuleCategory = new TQuestionRuleCategory();
        tQuestionRuleCategory.setQuestionRuleCategoryName(questionRuleCategoryReqVO.getQuestionRuleCategoryName());
        tQuestionRuleCategory.setQuestionRuleCategoryId(questionRuleCategoryId);

        if (questionRuleCategoryReqVO.getParentId() != null) {
            TQuestionRuleCategory node = this.getById(questionRuleCategoryId);
            TQuestionRuleCategory parent = this.getById(questionRuleCategoryReqVO.getParentId());
            tQuestionRuleCategoryMapper.moveNode(NestSetUtil.generateMoveNodeInputParams(node, parent));
        }

        return super.updateById(tQuestionRuleCategory);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean deleteById(Integer questionRuleCategoryId) {
        TQuestionRuleCategory tQuestionRuleCategory = this.getById(questionRuleCategoryId);

        if (!tQuestionRuleCategory.getCanOperate()) {
            return false;
        }

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.between("lft", tQuestionRuleCategory.getLft(), tQuestionRuleCategory.getRgt());
        this.remove(queryWrapper);

        tQuestionRuleService.updateNullCategoryId();

        Integer width = tQuestionRuleCategory.getRgt() - tQuestionRuleCategory.getLft() + 1;

        tQuestionRuleCategoryMapper.updateForDelete(width, tQuestionRuleCategory.getRgt());

        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TQuestionRuleCategory create(QuestionRuleCategoryReqVO questionRuleCategoryReqVO) {
        TQuestionRuleCategory parentPO = this.getById(questionRuleCategoryReqVO.getParentId());
        tQuestionRuleCategoryMapper.updateForCreate(parentPO.getLft());
        TQuestionRuleCategory tQuestionRuleCategory = new TQuestionRuleCategory();
        tQuestionRuleCategory.setQuestionRuleCategoryName(questionRuleCategoryReqVO.getQuestionRuleCategoryName());
        tQuestionRuleCategory.setLft(parentPO.getLft() + 1);
        tQuestionRuleCategory.setRgt(parentPO.getLft() + 2);
        this.save(tQuestionRuleCategory);

        return tQuestionRuleCategory;
    }
}
