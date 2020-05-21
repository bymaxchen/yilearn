package com.zhuiyi.ms.learn.service.download;

import com.zhuiyi.ms.learn.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * @author rodbate
 * @Title: ExcelDownload
 * @ProjectName learn
 * @Description: TODO
 * @date 2018/11/2111:52
 */
@Service
public class ExcelDownload {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelDownload.class);

//    public void downLoadExcel(String fliePath,HttpServletResponse response,String downloadType) throws Exception{
//
//        if (fliePath != null) {
//
//            // 设置文件路径
//            File file = new File(fliePath);
//            if (file.exists()) {
//                // 设置强制下载不打开
//                response.setContentType("application/force-download");
//                String fileName = FileUtil.getFileName(downloadType);
//                // 设置文件名
//                response.addHeader("Content-Disposition", "attachment;fileName=" +
//                        URLEncoder.encode(fileName, "UTF-8"));
//                byte[] buffer = new byte[1024];
//                FileInputStream fis = null;
////                BufferedInputStream bis = null;
//                try {
//                    fis = new FileInputStream(file);
////                    bis = new BufferedInputStream(fis);
//                    OutputStream os = response.getOutputStream();
//                    int len = 0;
//                    while ((len = fis.read(buffer)) > 0) {
//                        os.write(buffer, 0, len);
//                    }
//
//                    fis.close();
//                    os.close();
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
////                    if (bis != null) {
////                        try {
////                            bis.close();
////                        } catch (IOException e) {
////                            e.printStackTrace();
////                        }
////                    }
////                    if (fis != null) {
////                        try {
////                            fis.close();
////                        } catch (IOException e) {
////                            e.printStackTrace();
////                        }
////                    }
//                }
//            }
//        }
//    }

    public void downLoadExcel(String fliePath,HttpServletResponse response,String downloadType) throws Exception{

        if (fliePath != null) {
            //设置文件路径
            File file = new File(fliePath);
            if (file.exists()) {
                // 设置强制下载不打开
                response.setContentType("application/force-download");
                String fileName = FileUtil.getFileName(downloadType);
                // 设置文件名
                response.addHeader("Content-Disposition", "attachment;fileName=" +
                        URLEncoder.encode(fileName, "UTF-8"));
                response.setContentLength((int) file.length());
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
