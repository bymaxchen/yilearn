package com.zhuiyi.ms.learn.common;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

/**
 * 此类的描述是：日期工具类
 *
 * @author Charlesji@wezhuiyi.com
 * @create 2018-02-08 13:54
 **/
public class DateUtil {

	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
    private DateUtil() {
    }

    public static String dateSignToDateMonth(Integer dateSign) {
    	if(dateSign == null || dateSign == 0) {
    		return null;
    	}
    	return String.valueOf(dateSign).substring(0, 6);
    }
    
    // 时间戳转为指定的日期字符串
    public static String timestamp2String(Long timestamp, String formatter) {
    	LocalDateTime localDateTime = date2LocalDateTime(timestamp);
    	return localDateTime.format(DateTimeFormatter.ofPattern(formatter));
    }
    
    // 时间戳转为指定的日期格式
    public static Date timestamp2Date(Long timestamp, String formatter) throws ParseException {
    	LocalDateTime localDateTime = date2LocalDateTime(timestamp);
    	return strToDate(localDateTime.format(DateTimeFormatter.ofPattern(formatter)));
    }

    public static String date2String(Date date, String formatter) {
        LocalDateTime localDateTime = date2LocalDateTime(date);
        return localDateTime.format(DateTimeFormatter.ofPattern(formatter));
    }
    
    public static String dateToStr(Date date) {
		if (date == null) {
            return null;
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat(DATE_TIME_FORMAT);
            return formatter.format(date);
        }
    }

    public static String localDateTime2String(LocalDateTime localDateTime, String formatter) {
        return localDateTime.format(DateTimeFormatter.ofPattern(formatter));
    }

    public static LocalDateTime date2LocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDate = instant.atZone(zoneId).toLocalDateTime();
        return localDate;
    }

    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }

    public static LocalDateTime date2LocalDateTime(Long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDate = instant.atZone(zoneId).toLocalDateTime();
        return localDate;
    }

    public static long localDateTime2Timestamp(LocalDateTime localDateTime){
        Timestamp timestamp = Timestamp.valueOf(localDateTime);
        return timestamp.getTime();
    }
    
    // 日期转时间戳
    public static long dateTime2Timestamp(Date dateTime){
        return localDateTime2Timestamp(date2LocalDateTime(dateTime));
    }

    public static String TimestampToString(Timestamp timestamp,String formatter){
        DateFormat sdf = new SimpleDateFormat(formatter);
        return  sdf.format(timestamp);
    }

    public static boolean isSameMonth(Long startTimstamp, Long endTimestamp) {
    	if(startTimstamp==null||endTimestamp ==null ) {
    		return true;
    	}
        LocalDateTime startLocalDateTime = date2LocalDateTime(startTimstamp);
        LocalDateTime endLocalDateTime = date2LocalDateTime(endTimestamp);
        int startMonth = startLocalDateTime.getMonthValue();
        int endMonth = endLocalDateTime.getMonthValue();
        return startMonth == endMonth;
    }
    public static boolean isSameDay(Long startTimstamp, Long endTimestamp) {
    	if(startTimstamp==null||endTimestamp ==null ) {
    		return false;
    	}
    	LocalDateTime startLocalDateTime = date2LocalDateTime(startTimstamp);
    	LocalDateTime endLocalDateTime = date2LocalDateTime(endTimestamp);
    	int startMonth = startLocalDateTime.getDayOfYear();
    	int endMonth = endLocalDateTime.getDayOfYear();
    	return startMonth == endMonth;
    }

    // 日期字符串转date
    public static Date strToDate(String dateStr) throws ParseException{
 		String temp = dateStr;
 		if (dateStr.length() == 10) {
 			temp = temp + " 00:00:00";
 		}
 		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 		Date date = null;
 		date = ft.parse(temp);
         return date;
 	}
    
    // 计算两个日期的分钟差
    public static long computeMinutesDifference(Date smallDate, Date bigDate) {
        long minutes1 = TimeUnit.MILLISECONDS.toMinutes(smallDate.getTime());
        long minutes2 = TimeUnit.MILLISECONDS.toMinutes(bigDate.getTime());
        return minutes2 - minutes1;
    }
    // 计算两个日期的秒差
    public static long computeSecondsDifference(Date smallDate, Date bigDate) {
    	long seconds1 = TimeUnit.MILLISECONDS.toSeconds(smallDate.getTime());
        long seconds2 = TimeUnit.MILLISECONDS.toSeconds(bigDate.getTime());
        return seconds2 - seconds1;
    }
    
    // 计算两个时间戳的秒差
    public static long computeSecondsDifference(Long small, Long big) throws ParseException {
        return computeSecondsDifference(timestamp2Date(small, "yyyy-MM-dd HH:mm:ss"), timestamp2Date(big, "yyyy-MM-dd HH:mm:ss"));
    }

    // 计算时间戳与日期的秒差
	public static long computeSecondsDifference(Long small, Date big) throws ParseException {
		return computeSecondsDifference(timestamp2Date(small, "yyyy-MM-dd HH:mm:ss"), big);
	}
	
	// 计算时间戳与日期的秒差
	public static long computeSecondsDifference(Date small, Long big) throws ParseException {
		return computeSecondsDifference(small, timestamp2Date(big, "yyyy-MM-dd HH:mm:ss"));
	}
	
	// 获取当前时间的小时
	public static Integer getCurrentTimeHour() {
		Calendar now = Calendar.getInstance();
		int hour  = now.get(Calendar.HOUR_OF_DAY);
		return hour;
	}

    // 获取当前时间的date_sign
    public static Integer Date2DateSign(Date date) {
        if (date == null) {
            return null;
        }
        LocalDateTime localDateTime = date2LocalDateTime(date);
        String year = String.valueOf(localDateTime.getYear());
        String month = localDateTime.getMonthValue() < 10 ? "0" + localDateTime.getMonthValue() : String.valueOf(localDateTime.getMonthValue());
        String day = String.valueOf(localDateTime.getDayOfMonth());
        day = Integer.parseInt(day) < 10 ? "0" + day : day;
        return Integer.parseInt(year + month + day);
    }

    /**获取今天的开始时间 */
    public static Date getTodayStartTime() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime();
    }

    /**获取当天的结束时间*/
    public static Date getTodayEndTime() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    /**获取昨天的开始时间*/
    public static Date getYesterdayStartTime() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getTodayStartTime());
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    /**获取昨天的结束时间*/
    public static Date getYesterdayEndTime() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getTodayEndTime());
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    public static java.sql.Timestamp getTimestamp(Date date) {
        return new java.sql.Timestamp(date.getTime());
    }

}
