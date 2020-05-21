package com.zhuiyi.ms.learn.service.api;

import com.zhuiyi.ms.learn.entity.TQuestionCategory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhuiyi.ms.learn.entity.bo.QuestionCategoryTreeNodeBO;
import com.zhuiyi.ms.learn.entity.vo.req.QuestionCategoryReqVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bymax
 * @since 2019-10-15
 */
public interface ITQuestionCategoryService extends IService<TQuestionCategory> {
    /**
     * 根据一个根节点，获取其所有子节点以及其本身id的list
     * @param rootId
     * @return
     */
    List<Integer> findIdListByRootId(Integer rootId);

    List<QuestionCategoryTreeNodeBO> findAll(Integer type);

    Boolean updateById(Integer questionCategoryId, QuestionCategoryReqVO questionCategoryReqVO);

    Boolean deleteById(Integer questionCategoryId);

    TQuestionCategory create(QuestionCategoryReqVO questionCategoryReqVO);


}
