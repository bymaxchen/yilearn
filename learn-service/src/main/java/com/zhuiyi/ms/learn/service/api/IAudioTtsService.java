package com.zhuiyi.ms.learn.service.api;

/**
 * @author rodbate
 * @Title: IAudioTTSService
 * @ProjectName learn
 * @Description: TODO
 * @date 2020/1/311:06
 */
public interface IAudioTtsService {

    /**
      * @Description: 保存tts
    　* @param ${tags}
    　* @return ${return_type}
    　* @throws
    　* @author kloazhang
    　* @date 2020/1/3 11:07
    */
    Object saveAudioTts(Object object) throws Exception;

    /**
      * @Description: 获取tts
    　* @param ${tags}
    　* @return ${return_type}
    　* @throws
    　* @author ${USER}
    　* @date 2020/1/3 11:08
    */
    Object getAudioTts(Object object) throws Exception;

}
