package com.zhuiyi.ms.learn.service.impl;

import com.zhuiyi.ms.learn.entity.TPaperPart;
import com.zhuiyi.ms.learn.entity.vo.req.PaperCategoryReqVO;
import com.zhuiyi.ms.learn.mapper.TPaperPartMapper;
import com.zhuiyi.ms.learn.service.api.ITPaperPartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bymax
 * @since 2019-10-16
 */
@Service
public class TPaperPartServiceImpl extends ServiceImpl<TPaperPartMapper, TPaperPart> implements ITPaperPartService {
}
