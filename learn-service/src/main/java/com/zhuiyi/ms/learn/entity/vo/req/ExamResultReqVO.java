package com.zhuiyi.ms.learn.entity.vo.req;

import com.zhuiyi.ms.learn.entity.TExam;
import com.zhuiyi.ms.learn.entity.bo.ExamBO;
import com.zhuiyi.ms.learn.entity.bo.ExamPartBO;
import lombok.Data;

import java.util.List;

@Data
public class ExamResultReqVO {
    private ExamBO overview;

    private List<ExamPartBO> partList;
    // 友邦专用
    private String identityCode;

    // 友邦专用
    private String name;


}
