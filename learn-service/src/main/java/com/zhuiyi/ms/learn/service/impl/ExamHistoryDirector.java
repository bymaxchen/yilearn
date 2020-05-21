package com.zhuiyi.ms.learn.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.zhuiyi.ms.learn.entity.bo.ExamBO;
import com.zhuiyi.ms.learn.entity.vo.req.ExamResultReqVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author rodbate
 * @Title: ExamHistoryDirector
 * @ProjectName learn
 * @Description: TODO
 * @date 2019/12/3010:15
 */
@DS("#session.dataSourceName")
@Service
public class ExamHistoryDirector {

    @Value("${education.isPushEducation}")
    private Boolean isPushEducation;

    @Value("${spring.profiles.active}")
    private String active;

    @Autowired
    private ExamHistoryServiceImpl examHistoryService;


    public String perform(ExamResultReqVO examResultReqVO) {

        // 智能教育推送
        if (null != isPushEducation && isPushEducation) {
            examHistoryService.pushEducation(examResultReqVO);
        }


        // 保存会话历史
        ExamBO examBO = (ExamBO) examHistoryService.saveExamHistory(examResultReqVO);


        // 友邦推送
        if (active.equals("yb")) {
            examHistoryService.ybPushResult(examBO);
        }



        // 讯飞推送---讯飞推送

        // 浩宜推送

        // 众安推送
        return examBO.getExamId();
    }

}
