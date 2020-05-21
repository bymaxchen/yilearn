package com.zhuiyi.ms.learn.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhuiyi.ms.learn.entity.TSceneRuleCategory;
import com.zhuiyi.ms.learn.entity.bo.SceneRuleCategoryTreeNodeBO;
import com.zhuiyi.ms.learn.entity.vo.req.SceneRuleCategoryReqVO;
import com.zhuiyi.ms.learn.mapper.TSceneRuleCategoryMapper;
import com.zhuiyi.ms.learn.service.api.ITSceneRuleCategoryService;
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
 * @since 2019-10-22
 */
@Service
@DS("#session.dataSourceName")
public class TSceneRuleCategoryServiceImpl extends ServiceImpl<TSceneRuleCategoryMapper, TSceneRuleCategory> implements ITSceneRuleCategoryService {
    @Resource
    private TSceneRuleCategoryMapper tSceneRuleCategoryMapper;

    @Resource
    private TSceneRuleServiceImpl tSceneRuleService;

    @Override
    public List<SceneRuleCategoryTreeNodeBO> findAll() {
        SceneRuleCategoryTreeNodeBO sceneRuleCategoryTreeNodeBO = new SceneRuleCategoryTreeNodeBO();

        return sceneRuleCategoryTreeNodeBO.getTree(tSceneRuleCategoryMapper.findAllWithDepth(), tSceneRuleCategoryMapper.findAllWithSceneCount());
    }

    @Override
    public List<Integer> findIdListByRootId(Integer rootId) {
        TSceneRuleCategory tSceneRuleCategory = this.getById(rootId);

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.gt("lft", tSceneRuleCategory.getLft());
        queryWrapper.lt("rgt", tSceneRuleCategory.getRgt());

        List<TSceneRuleCategory> tSceneCategoryList = this.list(queryWrapper);

        List<Integer> idList = new ArrayList<>(Arrays.asList(rootId));
        for(TSceneRuleCategory item : tSceneCategoryList) {
            idList.add(item.getSceneRuleCategoryId());
        }
        return idList;
    }

    @Override
    public Boolean updateById(Integer sceneRuleCategoryId, SceneRuleCategoryReqVO sceneRuleCategoryReqVO) {
        TSceneRuleCategory tSceneRuleCategory = new TSceneRuleCategory();
        tSceneRuleCategory.setSceneRuleCategoryName(sceneRuleCategoryReqVO.getSceneRuleCategoryName());
        tSceneRuleCategory.setSceneRuleCategoryId(sceneRuleCategoryId);

        if (sceneRuleCategoryReqVO.getParentId() != null) {
            TSceneRuleCategory node = this.getById(sceneRuleCategoryId);
            TSceneRuleCategory parent = this.getById(sceneRuleCategoryReqVO.getParentId());
            tSceneRuleCategoryMapper.moveNode(NestSetUtil.generateMoveNodeInputParams(node, parent));
        }

        return super.updateById(tSceneRuleCategory);
    }

    /**
     *  TODO 根据can operate
     * @param sceneRuleCategoryId
     * @return
     */

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean deleteById(Integer sceneRuleCategoryId, Integer type) {
        TSceneRuleCategory tSceneRuleCategory = this.getById(sceneRuleCategoryId);

        if (!tSceneRuleCategory.getCanOperate()) {
            return false;
        }

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.between("lft", tSceneRuleCategory.getLft(), tSceneRuleCategory.getRgt());
        this.remove(queryWrapper);

        tSceneRuleService.updateNullCategoryId(type);

        Integer width = tSceneRuleCategory.getRgt() - tSceneRuleCategory.getLft() + 1;

        tSceneRuleCategoryMapper.updateForDelete(width, tSceneRuleCategory.getRgt());

        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TSceneRuleCategory create(SceneRuleCategoryReqVO sceneRuleCategoryReqVO) {
        TSceneRuleCategory parentPO = this.getById(sceneRuleCategoryReqVO.getParentId());
        tSceneRuleCategoryMapper.updateForCreate(parentPO.getLft());
        TSceneRuleCategory tSceneRuleCategory = new TSceneRuleCategory();
        tSceneRuleCategory.setSceneRuleCategoryName(sceneRuleCategoryReqVO.getSceneRuleCategoryName());
        tSceneRuleCategory.setLft(parentPO.getLft() + 1);
        tSceneRuleCategory.setRgt(parentPO.getLft() + 2);
        this.save(tSceneRuleCategory);

        return tSceneRuleCategory;
    }


}
