package com.zhuiyi.ms.learn.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhuiyi.ms.learn.common.Constants;
import com.zhuiyi.ms.learn.entity.TQuestionCategory;
import com.zhuiyi.ms.learn.entity.bo.QuestionCategoryTreeNodeBO;
import com.zhuiyi.ms.learn.entity.vo.req.QuestionCategoryReqVO;
import com.zhuiyi.ms.learn.mapper.TQuestionCategoryMapper;
import com.zhuiyi.ms.learn.service.api.ITQuestionCategoryService;
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
 * @since 2019-10-15
 */
@Service
@DS("#session.dataSourceName")
public class TQuestionCategoryServiceImpl extends ServiceImpl<TQuestionCategoryMapper, TQuestionCategory> implements ITQuestionCategoryService {
    @Resource
    private TQuestionCategoryMapper tQuestionCategoryMapper;

    @Resource
    private TBaseQuestionServiceImpl tBaseQuestionService;

    @Override
    public List<Integer> findIdListByRootId(Integer rootId) {
        TQuestionCategory tQuestionCategory = this.getById(rootId);

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.gt("lft", tQuestionCategory.getLft());
        queryWrapper.lt("rgt", tQuestionCategory.getRgt());

        List<TQuestionCategory> tQuestionCategoryList = this.list(queryWrapper);

        List<Integer> idList = new ArrayList<>(Arrays.asList(rootId));
        for(TQuestionCategory item : tQuestionCategoryList) {
            idList.add(item.getQuestionCategoryId());
        }
        return idList;
    }

    @Override
    public List<QuestionCategoryTreeNodeBO> findAll(Integer type) {
        QuestionCategoryTreeNodeBO questionCategoryTreeNodeBO = new QuestionCategoryTreeNodeBO();

        return questionCategoryTreeNodeBO.getTree(tQuestionCategoryMapper.findAllWithDepth(type), tQuestionCategoryMapper.findAllWithQuestionCount(type));
    }

    @Override
    public Boolean updateById(Integer questionCategoryId, QuestionCategoryReqVO questionCategoryReqVO) {
        TQuestionCategory tQuestionCategory = new TQuestionCategory();
        tQuestionCategory.setQuestionCategoryName(questionCategoryReqVO.getQuestionCategoryName());
        tQuestionCategory.setQuestionCategoryId(questionCategoryId);

        if (questionCategoryReqVO.getParentId() != null) {
            TQuestionCategory node = this.getById(questionCategoryId);
            TQuestionCategory parent = this.getById(questionCategoryReqVO.getParentId());
            tQuestionCategoryMapper.moveNode(NestSetUtil.generateMoveNodeInputParams(node, parent));
        }

        return super.updateById(tQuestionCategory);
    }

    /**
     * 删除分类后，需要把该分类下的问题更改到未分类下
     * 目前的做法是，分类被删除后，问题的分类id为边为null
     * 然后将分类id为null的分类，改成对应的未分类id
     * @param questionCategoryId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean deleteById(Integer questionCategoryId) {
        TQuestionCategory tQuestionCategory = this.getById(questionCategoryId);

        if (!tQuestionCategory.getCanOperate()) {
            return false;
        }

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.between("lft", tQuestionCategory.getLft(), tQuestionCategory.getRgt());
        this.remove(queryWrapper);

        tBaseQuestionService.updateNullCategoryId(tQuestionCategory.getType());

        Integer width = tQuestionCategory.getRgt() - tQuestionCategory.getLft() + 1;

        tQuestionCategoryMapper.updateForDelete(width, tQuestionCategory.getRgt());

        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TQuestionCategory create(QuestionCategoryReqVO questionCategoryReqVO) {
        TQuestionCategory parentPO = this.getById(questionCategoryReqVO.getParentId());
        tQuestionCategoryMapper.updateForCreate(parentPO.getLft());
        TQuestionCategory tQuestionCategory = new TQuestionCategory();
        tQuestionCategory.setQuestionCategoryName(questionCategoryReqVO.getQuestionCategoryName());
        tQuestionCategory.setLft(parentPO.getLft() + 1);
        tQuestionCategory.setRgt(parentPO.getLft() + 2);
        tQuestionCategory.setType(questionCategoryReqVO.getType());
        this.save(tQuestionCategory);

        return tQuestionCategory;
    }
}
