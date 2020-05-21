package com.zhuiyi.ms.learn.dto.request;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.zhuiyi.ms.learn.dto.response.ListJsonDetailDto;
import com.zhuiyi.ms.learn.dto.response.VoiceAuthResponse;
import lombok.Data;

import java.io.Serializable;

/**
 * @author rodbate
 * @Title: TaskRemoteGhDto
 * @ProjectName learn
 * @Description: TODO
 * @date 2019/3/420:45
 */
@Data
public class TaskRemoteGhDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private String coopCode;
    private String frontUserId;
    private String accessToken;
    /**
     *  场景名称
     */
    private String sceneName;
    private Integer sceneId;
    private Integer sceneTypeId;

    /**
     *  场景类型
     */
    private String sceneType;
    private Integer examId;
    private Integer taskId;
    private Integer getScore;
    private ListJsonDetailDto jsonDetail;
    private VoiceAuthResponse voiceAuthResponse;
    private String voiceAuthToGuangHua;
    private String startTime;
    private String endTime;
}
