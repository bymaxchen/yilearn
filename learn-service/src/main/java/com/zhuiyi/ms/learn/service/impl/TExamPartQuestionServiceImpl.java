package com.zhuiyi.ms.learn.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.zhuiyi.ms.learn.entity.TExamPartQuestion;
import com.zhuiyi.ms.learn.mapper.TExamPartQuestionMapper;
import com.zhuiyi.ms.learn.service.api.ITExamPartQuestionService;
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
public class TExamPartQuestionServiceImpl extends ServiceImpl<TExamPartQuestionMapper, TExamPartQuestion> implements ITExamPartQuestionService {

}
