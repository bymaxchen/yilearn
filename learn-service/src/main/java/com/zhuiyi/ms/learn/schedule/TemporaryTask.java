package com.zhuiyi.ms.learn.schedule;


import com.zhuiyi.ms.learn.common.DateUtil;
import com.zhuiyi.ms.learn.util.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * @author rodbate
 * @Title: TemporaryTask
 * @ProjectName yipal
 * @Description: TODO
 * @date 2018/11/2021:06
 */
@Component
public class TemporaryTask {

    @Value("${excel.downLoadExcelUrl}")
    private String downLoadExcelUrl;

    @Scheduled(cron = "0 0 0 * * ? ")
    public void deleteTemporary(){

        StringBuffer fileName = new StringBuffer(downLoadExcelUrl);
        fileName.append("/").append(DateUtil.TimestampToString(new Timestamp(System.currentTimeMillis()),
                "yyyyMMdd"));
        FileUtil.deleteDirectory(fileName.toString());

    }

}
