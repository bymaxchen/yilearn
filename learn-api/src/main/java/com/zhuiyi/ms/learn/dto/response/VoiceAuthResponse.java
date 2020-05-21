package com.zhuiyi.ms.learn.dto.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @author rodbate
 * @Title: VoiceAuthResponse
 * @ProjectName learn
 * @Description: TODO
 * @date 2019/2/2814:34
 */
@Data
public class VoiceAuthResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *  status为1的时候表示注册/认证成功，此时code为1；
     *  status为0时，表示注册/认证失败，此时code为具体的错误码
     */
    private String status;

    private String code;

    private Float score;

    private String errorMsg;

    public VoiceAuthResponse(String status, String code, String errorMsg) {
        this.status = status;
        this.code = code;
        this.errorMsg = errorMsg;
    }

    public VoiceAuthResponse() {
    }

    @Override
    public String toString() {
        return "VoiceAuthResponse{" +
                "status='" + status + '\'' +
                ", code='" + code + '\'' +
                ", score=" + score +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }
}
