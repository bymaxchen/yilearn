package com.zhuiyi.ms.learn.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zhuiyi.ms.learn.dto.SessionContextHolder;
import com.zhuiyi.ms.learn.dto.request.ReqAudioTtsDTO;
import com.zhuiyi.ms.learn.entity.AudioTtsEntity;
import com.zhuiyi.ms.learn.mapper.AudioTtsMapper;
import com.zhuiyi.ms.learn.service.api.IAudioTtsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author rodbate
 * @Title: DfsTtsServiceImpl
 * @ProjectName learn
 * @Description: TODO
 * @date 2020/1/311:39
 */
@Service
@Slf4j
public class DfsTtsServiceImpl implements IAudioTtsService {

    @Autowired
    private AudioTtsMapper audioTtsMapper;

    @Value("${ttsurl}")
    private String ttsurl;

    @Value("${dfs.url}")
    private String dfsUrl;

    @Value("${dfs.supportType}")
    private String supportType;

    private static final int SUCCESS_STATUS_CODE = 200;

    private Integer TRY_NUMBER = 0;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Retryable(value = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 2000, multiplier = 1.5))
    public Object saveAudioTts(Object object) throws Exception {
        TRY_NUMBER++;
        log.info("{} num saveAudioTts", TRY_NUMBER);
        ReqAudioTtsDTO reqAudioTtsDTO = (ReqAudioTtsDTO) object;
        byte[] audioFile = reqAudioTtsDTO.getAudioFile().getBytes("utf-8");
        Calendar cal = Calendar.getInstance();
        String currentDay = "" + cal.get(Calendar.YEAR) + cal.get(Calendar.MONTH) + cal.get(Calendar.DATE);
        String randomStr = UUID.randomUUID().toString().substring(0, 6);
        String fileName = currentDay + "_" + randomStr + "_" + reqAudioTtsDTO.getTextId() + ".txt";
        String url = dfsUrl + "tame/dfs/uploadByByte?supportType=" + supportType + "&serveName=yipal&fileName="
                + fileName;
        if (null != audioFile && audioFile.length > 0) {
            RestTemplate client = new RestTemplate();
            HttpHeaders requestHeaders = new HttpHeaders();
            HttpEntity<byte[]> requestEntity = new HttpEntity<byte[]>(audioFile, requestHeaders);
            ResponseEntity<JSONObject> result = client.exchange(url, HttpMethod.POST, requestEntity, JSONObject.class);
            if (result.getStatusCodeValue() == SUCCESS_STATUS_CODE) {
                JSONObject httpBody = result.getBody();
                HashMap<String, Object> data = (HashMap<String, Object>) httpBody.get("data");
                log.info("save TTS " + httpBody.toJSONString());
                AudioTtsEntity audioTtsEntity = new AudioTtsEntity();
                audioTtsEntity.setTextId(reqAudioTtsDTO.getTextId());
                audioTtsEntity.setText(reqAudioTtsDTO.getText());
                String fileUrl = data.get("fileUrl").toString();
                audioTtsEntity.setAudioFilePath(fileUrl);
                audioTtsEntity.setStartTime(new Timestamp(System.currentTimeMillis()));
                // 采用Replace into语句，textId为唯一性索引，若出现重复的textId,则用最新的audioFilePath覆盖
                audioTtsMapper.replaceAudioTts(audioTtsEntity);
                // 采用Replace into语句, examId和题目id为唯一性索引，若出现重复的examId和textId,即重复推送，则更新之前的数据
                Map<String, Object> params = new HashMap<>();
                params.put("examId", reqAudioTtsDTO.getExamId());
                params.put("textId", reqAudioTtsDTO.getTextId());
                params.put("text", reqAudioTtsDTO.getText());
                params.put("startTime", new Timestamp(System.currentTimeMillis()));
                audioTtsMapper.saveExamAndTts(params);
            }
        }
        return SessionContextHolder.getRequestId();
    }

    @Override
    @Retryable(value = Exception.class, maxAttempts = 1, backoff = @Backoff(delay = 2000, multiplier = 1.5))
    public Object getAudioTts(Object object) throws Exception {
        ReqAudioTtsDTO reqAudioTtsDTO = (ReqAudioTtsDTO) object;
        Map<String, Object> params = new HashMap<>();
        ReqAudioTtsDTO reqAudio = new ReqAudioTtsDTO();
        AudioTtsEntity audioTtsEntity = null;
        if (StringUtils.isNotEmpty(reqAudioTtsDTO.getText())) {
            params.put("textId", md5Dncode(reqAudioTtsDTO.getText(), 16));
            audioTtsEntity = audioTtsMapper.getAudioTtsByTextId(params);
            if (null != audioTtsEntity && StringUtils.isNotEmpty(audioTtsEntity.getText())) {
                getAudioFromDfs(audioTtsEntity, reqAudio);
            }
        }
        return reqAudio;
    }

    /**
     * 获取音频文件
     */
    ReqAudioTtsDTO getAudioFromDfs(AudioTtsEntity audioTtsEntity, ReqAudioTtsDTO reqAudio) throws Exception {
        TRY_NUMBER++;
        log.info("{} num getAudioFromDfs", TRY_NUMBER);
        if (StringUtils.isNotEmpty(audioTtsEntity.getAudioFilePath())) {
            String url = audioTtsEntity.getAudioFilePath();
            RestTemplate client = new RestTemplate();
            HttpEntity<String> requestEntity = new HttpEntity<String>("");
            ResponseEntity<byte[]> result = client.exchange(url, HttpMethod.POST, requestEntity, byte[].class);
            if (result.getStatusCodeValue() == SUCCESS_STATUS_CODE) {
                byte[] body = result.getBody();
                reqAudio.setAudioFile(new String(body));
            }
        }
        reqAudio.setTextId(audioTtsEntity.getTextId());
        reqAudio.setText(audioTtsEntity.getText());
        return reqAudio;
    }

    /**
     * @Description: md5加密 @param ${tags} @return ${return_type} @throws @author
     *               ${USER} @date 2019/5/28 20:52
     */
    public String md5Dncode(String sourceStr, Integer flag) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
            if (null != flag && flag == 16) {
                return buf.toString().substring(8, 24);
            }
        } catch (NoSuchAlgorithmException e) {
            log.info("md5Dncode ... {}", e.toString());
        }
        return result;
    }

    @Recover
    public void recover(Exception ex) {
        log.info("failed to dfs cause: {}", ex.toString());
    }

}
