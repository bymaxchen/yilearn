package com.zhuiyi.ms.learn.dto.request;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author rodbate
 * @Title: ChatLogDto
 * @ProjectName learn
 * @Description: TODO
 * @date 2018/9/2019:10
 */
@Data
public class ChatLogDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private String content;
    private int identity;
    private String chatId;
    private Date startTime;
    private String startTimestamp;
    private String endTimestamp;
    private Integer groupId;
    /**
     *  0: 未违规； 1：违规 ;默认0
     */
    private Integer isViolation;
}
