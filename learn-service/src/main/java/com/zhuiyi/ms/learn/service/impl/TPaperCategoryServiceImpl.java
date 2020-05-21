package com.zhuiyi.ms.learn.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhuiyi.ms.learn.common.Constants;
import com.zhuiyi.ms.learn.entity.TPaper;
import com.zhuiyi.ms.learn.entity.TPaperCategory;
import com.zhuiyi.ms.learn.entity.bo.PaperCategoryTreeNodeBO;
import com.zhuiyi.ms.learn.entity.bo.ResponsePage;
import com.zhuiyi.ms.learn.entity.vo.req.PaperCategoryReqVO;
import com.zhuiyi.ms.learn.entity.vo.req.PaperListReqVO;
import com.zhuiyi.ms.learn.mapper.TPaperCategoryMapper;
import com.zhuiyi.ms.learn.service.api.ITPaperCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhuiyi.ms.learn.util.NestSetUtil;
import com.zhuiyi.ms.learn.util.PageBuilder;
import org.apache.commons.lang3.StringUtils;
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
public class TPaperCategoryServiceImpl extends ServiceImpl<TPaperCategoryMapper, TPaperCategory> implements ITPaperCategoryService {
    @Resource
    private TPaperCategoryMapper tPaperCategoryMapper;

    @Resource
    private TPaperServiceImpl tPaperService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean updateOne(Integer id, PaperCategoryReqVO paperCategoryReqVO) {
        TPaperCategory tPaperCategory = new TPaperCategory();
        tPaperCategory.setPaperCategoryId(id);
        tPaperCategory.setPaperCategoryName(paperCategoryReqVO.getPaperCategoryName());

        if (paperCategoryReqVO.getParentId() != null) {
            TPaperCategory node = this.getById(id);
            TPaperCategory parent = this.getById(paperCategoryReqVO.getParentId());
            tPaperCategoryMapper.moveNode(NestSetUtil.generateMoveNodeInputParams(node, parent));
        }

        return this.updateById(tPaperCategory);
    }

    @Override
    public List<PaperCategoryTreeNodeBO> findAll() {
        PaperCategoryTreeNodeBO paperCategoryTreeNodeBO = new PaperCategoryTreeNodeBO();

        return paperCategoryTreeNodeBO.getTree(tPaperCategoryMapper.findAllWithDepth(), tPaperCategoryMapper.findAllWithPaperCount());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TPaperCategory create(PaperCategoryReqVO paperCategoryReqVO) {
        TPaperCategory parentPO = this.getById(paperCategoryReqVO.getParentId());
        tPaperCategoryMapper.updateForCreate(parentPO.getLft());
        TPaperCategory tPaperCategory = new TPaperCategory();
        tPaperCategory.setPaperCategoryName(paperCategoryReqVO.getPaperCategoryName());
        tPaperCategory.setLft(parentPO.getLft() + 1);
        tPaperCategory.setRgt(parentPO.getLft() + 2);
        this.save(tPaperCategory);

        return tPaperCategory;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean deleteById(Integer paperCategoryId) {
        if (!Constants.canCategoryDeleted(paperCategoryId)) {
            return false;
        }

        TPaperCategory tPaperCategory = this.getById(paperCategoryId);

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.between("lft", tPaperCategory.getLft(), tPaperCategory.getRgt());
        this.remove(queryWrapper);

        tPaperService.updateNullCategoryId();

        Integer width = tPaperCategory.getRgt() - tPaperCategory.getLft() + 1;

        tPaperCategoryMapper.updateForDelete(width, tPaperCategory.getRgt());

        return true;
    }

    @Override
    public List<Integer> findIdListByRootId(Integer rootId) {
        TPaperCategory tPaperCategory = this.getById(rootId);

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.gt("lft", tPaperCategory.getLft());
        queryWrapper.lt("rgt", tPaperCategory.getRgt());

        List<TPaperCategory> tQuestionCategoryList = this.list(queryWrapper);

        List<Integer> idList = new ArrayList<>(Arrays.asList(rootId));
        for(TPaperCategory item : tQuestionCategoryList) {
            idList.add(item.getPaperCategoryId());
        }
        return idList;
    }
}
