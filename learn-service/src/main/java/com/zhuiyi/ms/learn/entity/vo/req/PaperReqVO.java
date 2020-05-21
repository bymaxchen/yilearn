package com.zhuiyi.ms.learn.entity.vo.req;

import com.zhuiyi.ms.learn.entity.vo.res.PaperPartResVO;
import lombok.Data;

import java.util.List;

@Data
public class PaperReqVO {

    private String paperName;

    private Integer level;

    private String intro;

    private Integer paperCategoryId;

    private Integer timeLimit;

    private Integer passScore;

    /**
     * 0只有问答题；1只有情景题；2两者都有
     */
    private Integer type;

    List<PaperPartReqVO> partList;
}
