package com.zhuiyi.ms.learn.mapper;

import com.zhuiyi.ms.learn.entity.AudioTtsEntity;
import java.util.*;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author rodbate
 * @Title: AudioTtsMapper
 * @ProjectName learn
 * @Description: TODO
 * @date 2019/5/1815:17
 */
@Mapper
public interface AudioTtsMapper {

    int replaceAudioTts(AudioTtsEntity audioTtsEntity);

    List<AudioTtsEntity> getAudioTts(Map<String,Object> params);

    AudioTtsEntity getAudioTtsByTextId(Map<String,Object> params);

    /**
      * @Description: 更新tts文件路径
    　* @param ${tags}
    　* @return ${return_type}
    　* @throws
    　* @author ${USER}
    　* @date 2019/5/21 16:38
    */
    int updateAudioTts(Map<String,Object> params);

    /**
      * @Description:  题目关联tts插入记录
    　* @param ${tags}
    　* @return ${return_type}
    　* @throws
    　* @author ${USER}
    　* @date 2019/5/28 20:02
    */
    int saveExamAndTts(Map<String,Object> params);

    /**
      * @Description:  批量软删除ExamTts记录
    　* @param ${tags}
    　* @return ${return_type}
    　* @throws
    　* @author ${USER}
    　* @date 2019/5/29 11:30
    */
    int deleteExamAndTts(Map<String,Object> params);

    /**
      * @Description:  获取未被复用得TTS
    　* @param ${tags}
    　* @return ${return_type}
    　* @throws
    　* @author ${USER}
    　* @date 2019/5/29 21:06
    */
    List<String> getNoUseTts();

    /**
      * @Description: 获取tts文件路径
    　* @param ${tags}
    　* @return ${return_type}
    　* @throws
    　* @author ${USER}
    　* @date 2019/5/29 21:07
    */
    List<String> getTtsPath(Map<String,Object> params);

    /**
      * @Description: 获取所有的tts，用于同步Dfs
    　* @param ${tags}
    　* @return ${return_type}
    　* @throws
    　* @author ${USER}
    　* @date 2020/1/7 15:07
    */
    List<AudioTtsEntity> listTts(Map<String,Object> params);

}
