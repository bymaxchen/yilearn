package com.zhuiyi.ms.learn.service;

import com.zhuiyi.ms.learn.dto.request.ReqAudioTtsDTO;
import com.zhuiyi.ms.learn.dto.request.ReqDeleteTtsDTO;
import com.zhuiyi.ms.learn.dto.request.TtsReqDTO;
import com.zhuiyi.ms.learn.entity.AudioTtsEntity;
import com.zhuiyi.ms.learn.mapper.AudioTtsMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.UUID;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author rodbate
 * @Title: AudioTtsService
 * @ProjectName learn
 * @Description: TODO
 * @date 2019/5/1614:35
 */
@Service
public class AudioTtsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AudioTtsService.class);

    @Autowired
    private AudioTtsMapper audioTtsMapper;

    @Value("${ttsurl}")
    private String ttsurl;

    @Transactional(rollbackFor = Exception.class)
    public String saveAudio(ReqAudioTtsDTO reqAudioTtsDTO) throws IOException{
        byte[] audioFile = reqAudioTtsDTO.getAudioFile().getBytes("utf-8");
        Calendar cal = Calendar.getInstance();
        String currentDay = "" + cal.get(Calendar.YEAR) + cal.get(Calendar.MONTH) + cal.get(Calendar.DATE);
        String path = ttsurl;
        String fileName = currentDay+ "_" + reqAudioTtsDTO.getTextId() + ".txt";
        writeFile(path, fileName, audioFile);
        AudioTtsEntity audioTtsEntity = new AudioTtsEntity();
        audioTtsEntity.setTextId(reqAudioTtsDTO.getTextId());
        audioTtsEntity.setText(reqAudioTtsDTO.getText());
        LOGGER.info("AudioPath"+ path+fileName);
        audioTtsEntity.setAudioFilePath(path+fileName);
        audioTtsEntity.setStartTime(new Timestamp(System.currentTimeMillis()));
        //采用Replace into语句，textId为唯一性索引，若出现重复的textId,则用最新的audioFilePath覆盖
        audioTtsMapper.replaceAudioTts(audioTtsEntity);
        //t_exam_tts 如何记录？若出现重复推送呢?
        Map<String,Object> params = new HashMap<>();
        params.put("examId",reqAudioTtsDTO.getExamId());
        params.put("textId", reqAudioTtsDTO.getTextId());
        params.put("text",reqAudioTtsDTO.getText());
        params.put("startTime",new Timestamp(System.currentTimeMillis()));
        audioTtsMapper.saveExamAndTts(params);
        return String.valueOf(Thread.currentThread().getName());
    }

    public void writeFile(String path, String fileName, byte[] content)
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

    public ReqAudioTtsDTO getAudioByText(ReqAudioTtsDTO reqAudioTtsDTO){

        Map<String,Object> params = new HashMap<>();
        ReqAudioTtsDTO reqAudio = new ReqAudioTtsDTO();
        if(StringUtils.isNotEmpty(reqAudioTtsDTO.getText())){
            params.put("textId", md5Dncode(reqAudioTtsDTO.getText(),16));
            AudioTtsEntity audioTtsEntity = audioTtsMapper.getAudioTtsByTextId(params);
            if(null != audioTtsEntity && StringUtils.isNotEmpty(reqAudioTtsDTO.getText())){
                if(StringUtils.isNotEmpty(audioTtsEntity.getText()) && audioTtsEntity.getText().equals(reqAudioTtsDTO.getText())){
                    byte[] audioFile = getBytes(audioTtsEntity.getAudioFilePath());
                    reqAudio.setTextId(audioTtsEntity.getTextId());
//                    String audio = Base64.encodeBase64String(audioFile);
                    String audio = new String(audioFile);
                    reqAudio.setAudioFile(audio);
                    reqAudio.setText(audioTtsEntity.getText());
                }
            }
        }
        return reqAudio;
    }

    /**
      * @Description: 删除t_exam_tts:批量软删除
    　* @param ${tags}
    　* @return ${return_type}
    　* @throws
    　* @author kloazhang
    　* @date 2019/5/29 11:25
    */
    @Transactional(rollbackFor = Exception.class)
    public int deleteExamTtsRecord(ReqDeleteTtsDTO reqDeleteTtsDTO){
        int result = 0;
        if(CollectionUtils.isNotEmpty(reqDeleteTtsDTO.getTtsTexts())){
            Map<String,Object> params = new HashMap<>(2);
            params.put("examId", reqDeleteTtsDTO.getExamId());
            params.put("textIds",reqDeleteTtsDTO.getTtsTexts().stream().map(u -> u.getTextId()).collect(Collectors.toList()));
            result = audioTtsMapper.deleteExamAndTts(params);
        }
        return result;
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
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * 删除单个文件
     *
     * @param fileName
     *            要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public  boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("删除单个文件" + fileName + "成功！");
                return true;
            } else {
                System.out.println("删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            System.out.println("删除单个文件失败：" + fileName + "不存在！");
            return false;
        }
    }

    /**
      * @Description: md5加密
    　* @param ${tags}
    　* @return ${return_type}
    　* @throws
    　* @author ${USER}
    　* @date 2019/5/28 20:52
    */
    public  String md5Dncode(String sourceStr,Integer flag) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0){
                    i += 256;
                }
                if (i < 16){
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
            if(null != flag && flag == 16){
                return buf.toString().substring(8, 24);
            }
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        return result;
    }

}
