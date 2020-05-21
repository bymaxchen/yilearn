package com.zhuiyi.ms.learn.entity.vo.req;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class AudioReqVO {
    private String sessionId;

    private Integer type;

    private String mp3Data;

    private String pcmData;

    private String mp3Hash;

    @JSONField(name = "extends")
    private String extendsData;
}
