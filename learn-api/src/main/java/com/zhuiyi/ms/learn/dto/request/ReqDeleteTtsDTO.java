package com.zhuiyi.ms.learn.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.*;

import java.io.Serializable;

/**
 * @author rodbate
 * @Title: ReqDeleteTtsDTO
 * @ProjectName learn
 * @Description: TODO
 * @date 2019/5/2911:12
 */
@Data
public class ReqDeleteTtsDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     *  题目Id
     */
    @NotNull(message = "examId不可为null")
    private Integer examId;

    /**
     *  存放题目Id
     */
    @NotNull(message = "text_id不可为null")
    private List<TtsReqDTO> ttsTexts;
}
