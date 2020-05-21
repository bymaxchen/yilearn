package com.zhuiyi.ms.learn.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zhuiyi.ms.learn.entity.AudioTtsEntity;
import com.zhuiyi.ms.learn.mapper.AudioTtsMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.io.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * @author rodbate
 * @Title: TongBuTtsToDTS
 * @ProjectName learn
 * @Description: TODO
 * @date 2019/11/2015:37
 */
@Slf4j
@Service
public class TransAudioTtsFromLocalToDfs {

    @Autowired
    private AudioTtsMapper audioTtsMapper;

    private static final int SUCCESS_STATUS_CODE = 200;

    @Value("${ttsurl}")
    private String ttsurl;

    @Value("${dfs.url}")
    private String dfsUrl;

    @Value("${dfs.supportType}")
    private String supportType;

    @Value(("${dfs.open}"))
    private Boolean dfsOpen;

    public int transTtsToDfs() throws Exception{
        // 1. 获取tts录音文件
        Integer resultNum = 0;
        if(dfsOpen){
            log.info("start transfer tts to dfs ...");
            List<AudioTtsEntity> audioTtsEntityList = audioTtsMapper.listTts(null);
            if(CollectionUtils.isNotEmpty(audioTtsEntityList)){
                log.info("" + audioTtsEntityList.size());
                for(AudioTtsEntity audioTtsEntity : audioTtsEntityList){
                    log.info(audioTtsEntity.toString());
                    try{
                        updateDfs(audioTtsEntity,resultNum);
                    }catch (Exception ex){
                        log.info("transfer ttsId {} failed error: {}", audioTtsEntity.getTextId(), ex.toString());
                    }
                }
            }
        }
        return resultNum;
    }


    byte[] getBytes(String filePath){
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            log.info("filepath error ...");
            e.printStackTrace();
        } catch (IOException e) {
            log.info("i/o error ...");
            e.printStackTrace();
        }
        return buffer;
    }


    void updateDfs(AudioTtsEntity audioTtsEntity, Integer resultNum){
        if(StringUtils.isNotEmpty(audioTtsEntity.getAudioFilePath())){
            // 1. 上传dfs
            byte[] audioFile = getBytes(audioTtsEntity.getAudioFilePath());
            Calendar cal = Calendar.getInstance();
            String currentDay = "" + cal.get(Calendar.YEAR) + cal.get(Calendar.MONTH) + cal.get(Calendar.DATE);
            String randomStr = UUID.randomUUID().toString().substring(0, 6);
            String fileName = currentDay+ "_" + randomStr + "_" + audioTtsEntity.getTextId() + ".txt";
            String url = dfsUrl + "tame/dfs/uploadByByte?supportType="+supportType + "&serveName=yipal&fileName="+fileName;
            if(null != audioFile && audioFile.length > 0){
                RestTemplate client = new RestTemplate();
                HttpHeaders requestHeaders = new HttpHeaders();
                HttpEntity<byte[]> requestEntity = new HttpEntity<byte[]>(audioFile, requestHeaders);
                ResponseEntity<JSONObject> result = client.exchange(url, HttpMethod.POST, requestEntity, JSONObject.class);
                if(result.getStatusCodeValue() == SUCCESS_STATUS_CODE){
                    JSONObject httpBody = result.getBody();
                    HashMap<String, Object> data = (HashMap<String, Object>) httpBody.get("data");
                    log.info("save TTS " + httpBody.toJSONString());
                    String fileUrl = data.get("fileUrl").toString();
                    audioTtsEntity.setAudioFilePath(fileUrl);
                    audioTtsEntity.setStartTime(new Timestamp(System.currentTimeMillis()));
                    //采用Replace into语句，textId为唯一性索引，若出现重复的textId,则用最新的audioFilePath覆盖
                    audioTtsMapper.replaceAudioTts(audioTtsEntity);
                }
            }
            resultNum++;
        }
    }

    /**
      * @Description: 从dfs迁移至本地磁盘
    　* @param ${tags}
    　* @return ${return_type}
    　* @throws
    　* @author ${USER}
    　* @date 2020/1/7 16:53
    */
    @Transactional(rollbackFor = Exception.class)
   public int transTtsToLocal() throws Exception{
        //1. 遍历t_audio_record,获取已经存在的tts
       Integer resultNum = 0;
        if(!dfsOpen){
            log.info("start transfer tts to Local ...");
            List<AudioTtsEntity> audioTtsEntityList = audioTtsMapper.listTts(null);
            if(CollectionUtils.isNotEmpty(audioTtsEntityList)){
                for(AudioTtsEntity audioTtsEntity:audioTtsEntityList){
                    if(StringUtils.isNotEmpty(audioTtsEntity.getAudioFilePath())){
                        String url = audioTtsEntity.getAudioFilePath();
                        RestTemplate client = new RestTemplate();
                        HttpEntity<String> requestEntity = new HttpEntity<String>("");
                        //2. 根据tts的path记录，获取dfs文件
                        ResponseEntity<byte[]> result = client.exchange(url, HttpMethod.POST, requestEntity, byte[].class);
                        if(result.getStatusCodeValue() == SUCCESS_STATUS_CODE){
                            byte[] ttsText = result.getBody();
                            // 1. 将其写入本地，获取本地路径
                            Calendar cal = Calendar.getInstance();
                            String currentDay = "" + cal.get(Calendar.YEAR) + cal.get(Calendar.MONTH) + cal.get(Calendar.DATE);
                            String path = ttsurl;
                            String fileName = currentDay+ "_" + audioTtsEntity.getTextId() + ".txt";
                            writeFile(path, fileName, ttsText);
                            log.info("AudioPath"+ path+fileName);
                            audioTtsEntity.setAudioFilePath(path+fileName);
                            audioTtsEntity.setStartTime(new Timestamp(System.currentTimeMillis()));
                            //采用Replace into语句，textId为唯一性索引，若出现重复的textId,则用最新的audioFilePath覆盖
                            audioTtsMapper.replaceAudioTts(audioTtsEntity);
                            resultNum ++;
                        }
                    }
                }
            }
        }
        return resultNum;
    }

    void writeFile(String path, String fileName, byte[] content)
            throws IOException {
        try {
            File f = new File(path);
            if (!f.exists()) {
                f.mkdirs();
            }
            FileOutputStream fos = new FileOutputStream(path + fileName);
            fos.write(content);
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
