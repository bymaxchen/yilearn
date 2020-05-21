package com.zhuiyi.ms.learn.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhuiyi.ms.learn.async.UploadAudioAsync;
import com.zhuiyi.ms.learn.cache.AudioBytesCache;
import com.zhuiyi.ms.learn.common.enums.AudioEnum;
import com.zhuiyi.ms.learn.dto.SessionContextHolder;
import com.zhuiyi.ms.learn.entity.TAudio;
import com.zhuiyi.ms.learn.entity.vo.req.AudioReqVO;
import com.zhuiyi.ms.learn.entity.vo.res.AudioStatusResVO;
import com.zhuiyi.ms.learn.mapper.TAudioMapper;
import com.zhuiyi.ms.learn.service.api.ITAudioService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhuiyi.ms.learn.util.FileUtil;
import com.zhuiyi.ms.learn.util.HttpUtil;
import com.zhuiyi.ms.learn.util.LoggerUtil;
import com.zhuiyi.ms.learn.util.MathUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Optional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author bymax
 * @since 2020-02-13
 */
@Service
@DS("#session.dataSourceName")
public class TAudioServiceImpl extends ServiceImpl<TAudioMapper, TAudio> implements ITAudioService {
    @Autowired
    private AudioBytesCache audioBytesCache;

    @Autowired
    private UploadAudioAsync uploadAudioAsync;

    @Override
    public AudioStatusResVO getStatus(String sessionId) {
        AudioStatusResVO audioStatusResVO = new AudioStatusResVO();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("session_id", sessionId);
        TAudio audio = this.getOne(queryWrapper);

        // 音频未找到
        if (audio == null) {
            audioStatusResVO.setStatus(AudioEnum.NOT_FOUND.value());
        } else if (audio.getStatus().equals(AudioEnum.SUCCESS.value())) { // 音频拼接完成还需要把mp3地址返回
            audioStatusResVO.setStatus(audio.getStatus());
            audioStatusResVO.setAudioUrl(audio.getMp3Url());
        } else {
            audioStatusResVO.setStatus(audio.getStatus());
        }
        return audioStatusResVO;
    }

    @Override
    public Boolean saveAudio(AudioReqVO audioReqVO) {
        TAudio audio = new TAudio();
        String sessionId = audioReqVO.getSessionId();
        Integer type = audioReqVO.getType();

        audio.setSessionId(sessionId);

        Base64.Decoder dec = Base64.getDecoder();

        byte[] pcmBytes = dec.decode(Optional.ofNullable(audioReqVO.getPcmData()).orElse(""));
        byte[] mp3Bytes = dec.decode(Optional.ofNullable(audioReqVO.getMp3Data()).orElse(""));

        // 如果不是结束阶段，把bytes都缓存
        if (!type.equals(AudioEnum.END.value())) {
            audioBytesCache.saveBytes(sessionId, AudioEnum.MP3_TYPE.value(),
                    ArrayUtils.addAll(audioBytesCache.getBytes(sessionId, AudioEnum.MP3_TYPE.value()), mp3Bytes));
            audioBytesCache.saveBytes(sessionId, AudioEnum.PCM_TYPE.value(),
                    ArrayUtils.addAll(audioBytesCache.getBytes(sessionId, AudioEnum.PCM_TYPE.value()), pcmBytes));
        }

        // 第一个音频片段，此时在数据库创建一条记录
        if (audioReqVO.getType().equals(AudioEnum.START.value())) {
            audio.setStatus(AudioEnum.NOT_FINISHED.value());
            this.save(audio);
            // 拼接结束
        } else if (audioReqVO.getType().equals(AudioEnum.END.value())) {
            // 从缓存里读取 mp3 bytes
            byte[] mp3AllBytes = ArrayUtils.addAll(audioBytesCache.getBytes(sessionId, AudioEnum.MP3_TYPE.value()),
                    mp3Bytes);

            Integer status;

            // 比较md5
            if (audioReqVO.getMp3Hash().equals(FileUtil.getBase64MD5(mp3AllBytes))) {
                status = AudioEnum.SUCCESS.value();
            } else {
                status = AudioEnum.BROKEN.value();
            }
            // 异步上传mp3文件同时修改音频对应的状态
            uploadAudioAsync.upload(sessionId, MathUtil.getRandomNumberInts(100, 999) + ".mp3", mp3AllBytes,
                    AudioEnum.MP3_TYPE.value(), status, SessionContextHolder.getDbName());

            // 从缓存里读取 pcm bytes
            byte[] pcmAllBytes = ArrayUtils.addAll(pcmBytes,
                    audioBytesCache.getBytes(sessionId, AudioEnum.PCM_TYPE.value()));
            // 异步上传pcm文件
            uploadAudioAsync.upload(sessionId, MathUtil.getRandomNumberInts(100, 999) + ".pcm", pcmAllBytes,
                    AudioEnum.PCM_TYPE.value(), null, SessionContextHolder.getDbName());
        }

        return true;
    }

    @Override
    public ResponseEntity download(String sessionId, String range) {
        LoggerUtil.info("range : ======" + range);
        if (range != null) {
            LoggerUtil.info("range is  : ======" + range.equals("bytes=0-1"));
        }

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("session_id", sessionId);
        TAudio audio = this.getOne(queryWrapper);

        if (audio == null || StringUtils.isEmpty(audio.getMp3Url())) {
            LoggerUtil.error("audio not found or url is empty, sessionId:" + sessionId);
            return null;
        }

        byte[] tempBytes;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Vary", "Origin");
        httpHeaders.add("Cache-Control", "public,max-age=0");

        ResponseEntity.BodyBuilder builder;

        // 前端获取一个音频文件会发送两次请求
        // 第一个请求带有range，只拿一个字节
        // 第二个请求才获取所有资源，所以第一个请求需要缓存，以提高性能
        if (range != null && range.equals("bytes=0-1")) {
            byte[] bytes = HttpUtil.downloadBytesFromDfs(audio.getMp3Url());
            audioBytesCache.saveBytesOfDfs(sessionId, bytes);
            tempBytes = ArrayUtils.subarray(bytes, 0, 2);
            httpHeaders.add("Content-Range", "bytes 0-1/" + bytes.length);
            httpHeaders.add("Accept-Ranges", "bytes");
            builder = ResponseEntity.status(HttpStatus.PARTIAL_CONTENT);
        } else {
            tempBytes = audioBytesCache.getBytesOfDfs(sessionId);
            if (tempBytes.length == 0) {
                tempBytes = HttpUtil.downloadBytesFromDfs(audio.getMp3Url());
            }
            audioBytesCache.emptyBytesOfDfs(sessionId);
            builder = ResponseEntity.ok();
        }

        ByteArrayResource resource = new ByteArrayResource(tempBytes);
        return builder.headers(httpHeaders).contentLength(tempBytes.length)
                .contentType(MediaType.parseMediaType("audio/mpeg")).body(resource);
    }

    @Override
    public byte[] downloadByte(String sessionId, String range) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("session_id", sessionId);
        TAudio audio = this.getOne(queryWrapper);

        byte[] bytes = audioBytesCache.getBytesOfDfs(sessionId);

        if (bytes.length > 0) {
            return bytes;
        }

        bytes = HttpUtil.downloadBytesFromDfs(audio.getMp3Url());
        audioBytesCache.saveBytesOfDfs(sessionId, bytes);
        return bytes;
    }

    @Override
    public TAudio getByExamId(String examId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("exam_id", examId);
        return this.getOne(queryWrapper);
    }
}
