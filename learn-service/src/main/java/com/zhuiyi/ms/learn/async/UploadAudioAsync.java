package com.zhuiyi.ms.learn.async;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhuiyi.ms.learn.cache.AudioBytesCache;
import com.zhuiyi.ms.learn.common.enums.AudioEnum;
import com.zhuiyi.ms.learn.entity.TAudio;
import com.zhuiyi.ms.learn.mapper.TAudioMapper;
import com.zhuiyi.ms.learn.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class UploadAudioAsync {
    @Autowired
    private HttpUtil httpUtil;

    @Autowired
    private TAudioMapper audioMapper;

    @Autowired
    private AudioBytesCache audioBytesCache;

    /**
     * 上传音频文件的字节到dfs之后
     * 再更新数据库状态
     * @param sessionId
     * @param fileName
     * @param body
     * @param audioType
     * @param status
     * @param dbName
     * @return
     */
    @DS("#dbName")
    @Async("taskExecutor")
    public Boolean upload(String sessionId, String fileName, byte[] body, Integer audioType, Integer status, String dbName) {
        TAudio audio = new TAudio();
        audio.setSessionId(sessionId);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("session_id", sessionId);
        String audioUrl = httpUtil.uploadBytesToDfs(fileName, body);
        if (status != null) {
            audio.setStatus(status);
        }

        if (audioType.equals(AudioEnum.MP3_TYPE.value())) {
            audio.setMp3Url(audioUrl);
        } else {
            audio.setPcmUrl(audioUrl);
        }

        audioMapper.update(audio, queryWrapper);
        audioBytesCache.emptyBytes(sessionId, audioType);
        return true;
    }
}
