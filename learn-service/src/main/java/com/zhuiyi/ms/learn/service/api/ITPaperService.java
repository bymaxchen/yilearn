package com.zhuiyi.ms.learn.service.api;

import com.zhuiyi.ms.learn.entity.TPaper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhuiyi.ms.learn.entity.bo.ResponsePage;
import com.zhuiyi.ms.learn.entity.vo.req.PaperListReqVO;
import com.zhuiyi.ms.learn.entity.vo.req.PaperReqVO;
import com.zhuiyi.ms.learn.entity.vo.res.PaperResVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bymax
 * @since 2019-10-16
 */
public interface ITPaperService extends IService<TPaper> {
    ResponsePage findAll(PaperListReqVO paperListReqVO);

    TPaper create(PaperReqVO paperReqVO);

    Boolean updateOne(Integer id, PaperReqVO paperReqVO);

    Boolean RemoveResetFlag(Integer id);

    PaperResVO findOne(Integer id);

    List<TPaper> findAllByQuestionId(Integer id);

    /**
     *  把categoryId为null的更新为具体的未分类id
     * @return
     */
    Boolean updateNullCategoryId();
}
