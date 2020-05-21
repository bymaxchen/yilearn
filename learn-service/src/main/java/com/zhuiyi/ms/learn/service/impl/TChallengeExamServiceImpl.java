package com.zhuiyi.ms.learn.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhuiyi.ms.learn.entity.TChallengeExam;
import com.zhuiyi.ms.learn.entity.bo.ResponsePage;
import com.zhuiyi.ms.learn.entity.bo.SceneQuestionBO;
import com.zhuiyi.ms.learn.entity.vo.req.ChallengeExamReqVO;
import com.zhuiyi.ms.learn.entity.vo.res.ChallengeExamResVO;
import com.zhuiyi.ms.learn.entity.vo.res.SceneQuestionResVO;
import com.zhuiyi.ms.learn.mapper.TChallengeExamMapper;
import com.zhuiyi.ms.learn.service.api.ITChallengeExamService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhuiyi.ms.learn.util.PageBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bymax
 * @since 2019-12-30
 */
@Service
@DS("#session.dataSourceName")
public class TChallengeExamServiceImpl extends ServiceImpl<TChallengeExamMapper, TChallengeExam> implements ITChallengeExamService {
    @Resource
    private TSceneQuestionServiceImpl tSceneQuestionService;

    @Override
    public ResponsePage findAll(ChallengeExamReqVO challengeExamReqVO) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if (StringUtils.isNotEmpty(challengeExamReqVO.getKeyword())) {
            queryWrapper.like("challengeExamName", challengeExamReqVO.getKeyword());
        }

        queryWrapper.orderByDesc("update_time");

        Page page = challengeExamReqVO.getPage();
        if (page == null) {
            page = PageBuilder.getMaxPage();
        }

        return PageBuilder.buildResponsePage(this.page(page, queryWrapper));
    }

    @Override
    public TChallengeExam create(ChallengeExamReqVO challengeExamReqVO) {
        TChallengeExam tChallengeExam = new TChallengeExam();
        BeanUtils.copyProperties(challengeExamReqVO, tChallengeExam);
        this.save(tChallengeExam);
        return tChallengeExam;
    }

    @Override
    public ChallengeExamResVO findOne(Integer id) {
        TChallengeExam tChallengeExam = this.getById(id);

        List<SceneQuestionBO> idList = JSON.parseArray(tChallengeExam.getExamList(), SceneQuestionBO.class);

        ChallengeExamResVO challengeExamResVO = new ChallengeExamResVO();
        if (idList != null) {
            List<SceneQuestionResVO> sceneQuestionResVOList = tSceneQuestionService.findAll(idList.stream().map(item -> item.getId()).collect(Collectors.toList()));
            challengeExamResVO.setSceneQuestionResVOList(sceneQuestionResVOList);
        }

        BeanUtils.copyProperties(tChallengeExam, challengeExamResVO);
        return challengeExamResVO;
    }
}
