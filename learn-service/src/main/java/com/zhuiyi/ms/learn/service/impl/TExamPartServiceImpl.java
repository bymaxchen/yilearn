package com.zhuiyi.ms.learn.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.zhuiyi.ms.learn.entity.TExamPart;
import com.zhuiyi.ms.learn.mapper.TExamPartMapper;
import com.zhuiyi.ms.learn.service.api.ITExamPartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bymax
 * @since 2019-10-24
 */
@DS("#session.dataSourceName")
@Service
public class TExamPartServiceImpl extends ServiceImpl<TExamPartMapper, TExamPart> implements ITExamPartService {

}
