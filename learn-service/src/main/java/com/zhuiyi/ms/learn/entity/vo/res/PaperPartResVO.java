package com.zhuiyi.ms.learn.entity.vo.res;

import com.zhuiyi.ms.learn.entity.TPaperPart;
import lombok.Data;

import java.util.List;

@Data
public class PaperPartResVO extends TPaperPart {
    private List<PaperQuestionResVO> questionList;
}
