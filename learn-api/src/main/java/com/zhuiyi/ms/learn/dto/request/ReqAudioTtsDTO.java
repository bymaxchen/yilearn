package com.zhuiyi.ms.learn.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author rodbate
 * @Title: ReqAudioTtsDTO
 * @ProjectName learn
 * @Description: TODO
 * @date 2019/5/1614:31
 */
@Data
public class ReqAudioTtsDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "examId文本不可为空")
    private Integer examId;
    @NotNull(message = "text文本不可为空")
    private String textId;
    @NotNull(message = "audioFile不可为空")
    private String audioFile;

    private String text;

    @Override
    public String toString() {
        return "ReqAudioTtsDTO{" +
                "examId=" + examId +
                ", textId='" + textId + '\'' +
                ", audioFile='" + audioFile + '\'' +
                '}';
    }
}
