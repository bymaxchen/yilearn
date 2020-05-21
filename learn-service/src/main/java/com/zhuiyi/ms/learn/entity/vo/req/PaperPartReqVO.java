package com.zhuiyi.ms.learn.entity.vo.req;

import com.zhuiyi.ms.learn.entity.TPartQuestion;
import lombok.Data;

import java.util.List;

@Data
public class PaperPartReqVO {

    private Boolean isInOrder;

    private Boolean isRandom;

    private Integer randomCount;

    private Integer score;

    private String partName;

    private Integer type;

    List<TPartQuestion> questionList;
}
