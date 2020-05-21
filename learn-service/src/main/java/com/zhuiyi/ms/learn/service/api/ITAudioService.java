package com.zhuiyi.ms.learn.service.api;

import com.zhuiyi.ms.learn.entity.TAudio;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhuiyi.ms.learn.entity.vo.req.AudioReqVO;
import com.zhuiyi.ms.learn.entity.vo.res.AudioStatusResVO;
import org.springframework.http.ResponseEntity;

import javax.annotation.Resource;
import java.io.FileOutputStream;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bymax
 * @since 2020-02-13
 */
public interface ITAudioService extends IService<TAudio> {
    TAudio getByExamId(String examId);

    AudioStatusResVO getStatus(String sessionId);

    Boolean saveAudio(AudioReqVO audioReqVO);

    ResponseEntity download(String sessionId, String range);

    byte[] downloadByte(String sessionId, String range);
}
