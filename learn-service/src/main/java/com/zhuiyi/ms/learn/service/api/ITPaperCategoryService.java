package com.zhuiyi.ms.learn.service.api;

import com.zhuiyi.ms.learn.entity.TPaperCategory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhuiyi.ms.learn.entity.bo.PaperCategoryTreeNodeBO;
import com.zhuiyi.ms.learn.entity.bo.ResponsePage;
import com.zhuiyi.ms.learn.entity.vo.req.PaperCategoryReqVO;
import com.zhuiyi.ms.learn.entity.vo.req.PaperListReqVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bymax
 * @since 2019-10-16
 */
public interface ITPaperCategoryService extends IService<TPaperCategory> {
    Boolean updateOne(Integer id, PaperCategoryReqVO paperCategoryReqVO);

    List<PaperCategoryTreeNodeBO> findAll();

    TPaperCategory create(PaperCategoryReqVO paperCategoryReqVO);

    Boolean deleteById(Integer paperCategoryId);

    List<Integer> findIdListByRootId(Integer rootId);
}
