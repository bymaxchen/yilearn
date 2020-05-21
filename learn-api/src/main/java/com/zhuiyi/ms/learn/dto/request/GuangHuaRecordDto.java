package com.zhuiyi.ms.learn.dto.request;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author rodbate
 * @Title: GuangHuaRecordDto
 * @ProjectName learn
 * @Description: TODO
 * @date 2019/5/1714:37
 */
@Data
public class GuangHuaRecordDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private String dbName;
    private Integer id;
    /**
     *  0:推送中； 1：推送失败
     */
    private Integer status;

    /**
     *  推送记录
     */
    private String data;

    /**
     *  失败原因
     */
    private String exception;

    private Timestamp startTime;
    private Timestamp updateTime;

}
