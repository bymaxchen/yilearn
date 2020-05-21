package com.zhuiyi.ms.learn.schedule;

import com.zhuiyi.ms.learn.mapper.AudioTtsMapper;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.*;
import java.io.File;

/**
 * @author rodbate
 * @Title: ClearTtsTask
 * @ProjectName learn
 * @Description: TODO
 * @date 2019/5/2920:11
 */
@Component
public class ClearTtsTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClearTtsTask.class);

    @Autowired
    private AudioTtsMapper audioTtsMapper;

    @Value("${ttsurl}")
    private String ttsurl;

    @Scheduled(cron = "0 0 0 1/7 * ? ")
    public void clearTts(){

        //1. 获取未被复用得tts
        List<String> textIds = audioTtsMapper.getNoUseTts();
        if(CollectionUtils.isNotEmpty(textIds)){
            //2. 获取对应文件路径
            Map<String,Object> params = new HashMap<>();
            params.put("textIds",textIds);
            List<String> ttsFiles = audioTtsMapper.getTtsPath(params);
            if(CollectionUtils.isNotEmpty(ttsFiles)){
                //1. 删除文件路径
                for(String filePath : ttsFiles){
                    deleteFile(filePath);
                }
                //2. 删除t_audio_record表记录

            }
        }

    }


    /**
     * 删除单个文件
     *
     * @param fileName
     *            要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
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
}
