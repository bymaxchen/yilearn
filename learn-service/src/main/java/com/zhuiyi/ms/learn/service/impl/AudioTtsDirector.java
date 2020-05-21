package com.zhuiyi.ms.learn.service.impl;

import com.zhuiyi.ms.learn.mapper.AudioTtsMapper;
import com.zhuiyi.ms.learn.service.api.IAudioTtsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author rodbate
 * @Title: AudioTtsDirector
 * @ProjectName learn
 * @Description: TODO
 * @date 2020/1/311:53
 */
@Service
@Slf4j
public class AudioTtsDirector {

    @Autowired
    private LocalTtsServiceImpl localTtsService;

    @Autowired
    private DfsTtsServiceImpl dfsTtsService;

    private Boolean dfsOpen;

    private IAudioTtsService audioTtsService;

    public AudioTtsDirector(@Value("${dfs.open}") Boolean dfsOpen) {
        this.dfsOpen = dfsOpen;
    }

    /**
     * @Description: 根据配置tts本地还是dfs的实现类 @param ${tags} @return
     *               ${return_type} @throws @author ${USER} @date 2020/1/7 11:51
     */
    @PostConstruct
    public void inits() {
        if (dfsOpen) {
            this.audioTtsService = dfsTtsService;
        } else {
            this.audioTtsService = localTtsService;
        }
    }

    public Object saveAudioTts(Object object) throws Exception {
        return audioTtsService.saveAudioTts(object);
    }

    public Object getAudioTtsByText(Object object) throws Exception {
        return audioTtsService.getAudioTts(object);
    }

}
