package com.zhuiyi.ms.learn.service;

import com.alibaba.fastjson.JSONObject;
import com.zhuiyi.ms.learn.dto.request.RequestTaskLogsDto;
import com.zhuiyi.ms.learn.dto.request.TaskXFRemoteDto;
import com.zhuiyi.ms.learn.dto.response.VoiceAuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author rodbate
 * @Title: VoiceAuth
 * @ProjectName learn
 * @Description: TODO
 * @date 2019/2/2620:43
 */
@Service("voiceAuth")
public class VoiceAuth  {

    private static final Logger LOGGER = LoggerFactory.getLogger(VoiceAuth.class);

    private static final  Pattern IPANDPORT = Pattern.compile("/");

    public VoiceAuthResponse remoteCall(RequestTaskLogsDto requestTaskLogsDto) {

        TaskXFRemoteDto taskXFRemoteDto = new TaskXFRemoteDto();
        taskXFRemoteDto.setPosition(requestTaskLogsDto.getPosition());
        taskXFRemoteDto.setIdentifierId(requestTaskLogsDto.getUserData().getFrontUserId());
        taskXFRemoteDto.setType("1");
        taskXFRemoteDto.setSourceUrl(requestTaskLogsDto.getAudioSrc());
        taskXFRemoteDto.setAudioUrl(requestTaskLogsDto.getAudioSrc());
        String audioUrl = taskXFRemoteDto.getAudioUrl();
        if(requestTaskLogsDto.getPosition() > 0){
            audioUrl = getDestUrl(taskXFRemoteDto, requestTaskLogsDto);
        }
        //音频文件异常，不存在时，字段值为"-"
        if(!StringUtils.isEmpty(audioUrl) && !audioUrl.equals("-")){
            //***.mp3   ->  ***_00.pcm
            audioUrl = audioUrl.replace(".mp3", "_00.pcm");
        }
        taskXFRemoteDto.setAudioUrl(audioUrl);
        //1. 调用讯飞历史接口，获取声纹认证结果
        taskXFRemoteDto.setVoiceAuthToXunFei(requestTaskLogsDto.getVoiceAuthToXunFei());
        return voiceAuth(taskXFRemoteDto);
    }

    String getDestUrl(TaskXFRemoteDto taskXFRemoteDto,RequestTaskLogsDto requestTaskLogsDto){
        String sourceUrl = taskXFRemoteDto.getAudioUrl();
        Matcher slashMatcher = IPANDPORT.matcher(sourceUrl);
        int mIdx = 0;
        while(slashMatcher.find()) {
            mIdx++;
            //当"/"符号第三次出现的位置
            if(mIdx == taskXFRemoteDto.getPosition()){
                break;
            }
        }
        String prf = requestTaskLogsDto.getVoiceAuthIpPort();
        String suffix = sourceUrl.substring(slashMatcher.start(), sourceUrl.length());
        String audioUrl = prf + suffix;
        return audioUrl;
    }

    public VoiceAuthResponse voiceAuth(TaskXFRemoteDto taskXFRemoteDto){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("identifierId", taskXFRemoteDto.getIdentifierId());
        params.add("filePath", taskXFRemoteDto.getAudioUrl());
        params.add("type", taskXFRemoteDto.getType());
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<MultiValueMap<String,Object>> httpEntity = new HttpEntity(params,httpHeaders);
        LOGGER.info("voiceAudioSrc:" + taskXFRemoteDto.getSourceUrl());
        LOGGER.info("voice Request >>："+ httpEntity.toString());
        long begin = System.currentTimeMillis();
        ResponseEntity<String> baseResponse = restTemplate.postForEntity(taskXFRemoteDto.getVoiceAuthToXunFei(),httpEntity,String.class);
        long end = System.currentTimeMillis();
        LOGGER.info("声纹认证接口响应时间: "+ (end - begin) + "ms");
        String result = baseResponse.getBody();
        JSONObject response = JSONObject.parseObject(result);
        VoiceAuthResponse voiceAuthResponse = JSONObject.toJavaObject(response, VoiceAuthResponse.class);
        return voiceAuthResponse;
    }

}
