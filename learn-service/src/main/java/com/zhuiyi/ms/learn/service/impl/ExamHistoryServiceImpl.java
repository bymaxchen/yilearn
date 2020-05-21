package com.zhuiyi.ms.learn.service.impl;

import com.zhuiyi.ms.learn.async.EducationAsync;
import com.zhuiyi.ms.learn.async.YouBangExamPushAsync;
import com.zhuiyi.ms.learn.dto.SessionContextHolder;
import com.zhuiyi.ms.learn.entity.bo.ExamBO;
import com.zhuiyi.ms.learn.entity.vo.req.ExamResultReqVO;
import com.zhuiyi.ms.learn.entity.vo.res.ExamResultResVO;
import com.zhuiyi.ms.learn.service.api.IExamHistoryService;
import com.zhuiyi.ms.learn.util.HttpContextUtils;
import com.zhuiyi.ms.learn.util.LoggerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author rodbate
 * @Title: ExamHistoryServiceImpl
 * @ProjectName learn
 * @Description: TODO
 * @date 2019/12/3010:00
 */
@Service
public class ExamHistoryServiceImpl implements IExamHistoryService {

    @Autowired
    private TExamServiceImpl tExamService;

    @Autowired
    private EducationAsync educationAsync;

    @Autowired
    private YouBangExamPushAsync youBangExamPushAsync;

    /**
     * 保存考试历史
     *
     * @param
     * @return
     */
    @Override
    public Object saveExamHistory(Object examResultReqVO) {
        return tExamService.create((ExamResultReqVO) examResultReqVO);
    }

    /**
     * 友邦推送
     *
     * @param
     * @return
     */
    @Override
    public void ybPushResult(Object examBO) {
        youBangExamPushAsync.pushResult((ExamBO) examBO, HttpContextUtils.getDsFromSession());
    }

    /**
     * 智能教育-异步推送
     *
     * @param object
     */
    @Override
    public void pushEducation(Object object) {
        ExamResultReqVO examResultReqVO = (ExamResultReqVO) object;
        try {
            educationAsync.pushExam(examResultReqVO.getPartList(), SessionContextHolder.getDbName().substring(9));
        } catch (Exception ex) {
            LoggerUtil.info("push education error {}", ex.toString());
        }
    }

}
