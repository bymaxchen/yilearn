package com.zhuiyi.ms.learn.dto.response;

import java.util.*;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;

import java.io.Serializable;

/**
 * @author rodbate
 * @Title: GuangHuaResponse
 * @ProjectName learn
 * @Description: TODO
 * @date 2019/5/1714:54
 */
@Data
public class GuangHuaResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    private String ret;
    private String msg;
    private JSONArray data;
}
