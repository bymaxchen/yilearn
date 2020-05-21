package com.zhuiyi.ms.learn.util;


import com.zhuiyi.ms.learn.common.DateUtil;
import sun.misc.BASE64Encoder;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Base64;

/**
 * @author rodbate
 * @Title: ExcelUtil
 * @ProjectName learn
 * @Description: TODO
 * @date 2018/11/2111:27
 */
public class FileUtil {

    public static String getMD5SumByBytes(byte[] bytes) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(bytes);
            byte[] digest = md.digest();
            String md5Sum = DatatypeConverter
                    .printHexBinary(digest).toUpperCase();
            return md5Sum;
        } catch (NoSuchAlgorithmException e) {
            return "";
        }
    }

    public static String getBase64MD5(byte[] bytes) {
        try {
            BASE64Encoder encoder = new BASE64Encoder();
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(bytes);
            byte[] bMac = md.digest();
            String anotherString = encoder.encodeBuffer(bMac);
            return anotherString.replaceAll("\r|\n", "");
        } catch (NoSuchAlgorithmException e) {
            return "";
        }
    }

    public static String getFileName(String type){

        StringBuffer fileName = new StringBuffer("生成文件名");
        if(null != type && !type.isEmpty()){
            if(type.equals("serviceScoreStatistic")){
                fileName = new StringBuffer("learn坐席分数统计导出");
            }
            if(type.equals("opinion")){
                fileName = new StringBuffer("pal问题反馈导出");
            }
            int random=(int)(Math.random()*9000)+1000;
            fileName.append("_").append(String.valueOf(random));
            fileName.append("_").append(DateUtil.TimestampToString(new Timestamp(System.currentTimeMillis()),
                    "yyyyMMddHHmmss")).append(".xlsx");
        }

        return fileName.toString();
    }

    public static boolean createDir(String destDirName) {
        File dir = new File(destDirName);
        if (dir.exists()) {
            System.out.println("创建目录" + destDirName + "失败，目标目录已经存在");
            return false;
        }
        if (!destDirName.endsWith(File.separator)) {
            destDirName = destDirName + File.separator;
        }
        //创建目录
        if (dir.mkdirs()) {
            System.out.println("创建目录" + destDirName + "成功！");
            return true;
        } else {
            System.out.println("创建目录" + destDirName + "失败！");
            return false;
        }
    }

    public static String createTempFile(String prefix, String suffix, String dirName) {
        File tempFile = null;
        if (dirName == null) {
            try{
                //在默认文件夹下创建临时文件
                tempFile = File.createTempFile(prefix, suffix);
                //返回临时文件的路径
                return tempFile.getCanonicalPath();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("创建临时文件失败！" + e.getMessage());
                return null;
            }
        } else {
            File dir = new File(dirName);
            //如果临时文件所在目录不存在，首先创建
            if (!dir.exists()) {
                if (! FileUtil.createDir(dirName)) {
                    System.out.println("创建临时文件失败，不能创建临时文件所在的目录！");
                    return null;
                }
            }
            try {
                //在指定目录下创建临时文件
                tempFile = File.createTempFile(prefix, suffix, dir);
                return tempFile.getCanonicalPath();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("创建临时文件失败！" + e.getMessage());
                return null;
            }
        }
    }


    /**
     *  根据路径删除指定的目录，无论存在与否
     *@param sPath  要删除的目录path
     *@return 删除成功返回 true，否则返回 false。
     */
    public static boolean DeleteFolder(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 判断目录或文件是否存在
        if (!file.exists()) {  // 不存在返回 false
            return flag;
        } else {
            // 判断是否为文件
            if (file.isFile()) {  // 为文件时调用删除文件方法
                return deleteFile(sPath);
            } else {  // 为目录时调用删除目录方法
                return deleteDirectory(sPath);
            }
        }
    }

    /**
     * 删除单个文件
     * @param   sPath 被删除文件path
     * @return 删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    /**
     * 删除目录以及目录下的文件
     * @param   sPath 被删除目录的路径
     * @return  目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String sPath) {
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        boolean flag = true;
        //删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            //删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag){
                    break;
                }
            } //删除子目录
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) {
                    break;}

            }
        }
        if (!flag) {
            return false;
        }
        //删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }
}
