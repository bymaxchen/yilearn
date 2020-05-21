package com.zhuiyi.ms.learn.entity.vo.res;


import com.zhuiyi.ms.learn.entity.TExam;
import com.zhuiyi.ms.learn.entity.TExamPart;
import com.zhuiyi.ms.learn.entity.bo.ExamBO;
import com.zhuiyi.ms.learn.entity.bo.ExamPartBO;
import lombok.Data;

import java.util.List;

@Data
public class ExamResultResVO {
    private ExamBO overview;

    private List<ExamPartBO> partList;
}
