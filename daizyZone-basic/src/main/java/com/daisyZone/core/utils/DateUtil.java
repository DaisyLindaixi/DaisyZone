package com.daisyZone.core.utils;

import org.apache.commons.lang3.StringUtils;
import org.quartz.CronExpression;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 时间处理工具类
 *
 * @author jiaqi
 */
public class DateUtil {


    private static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 根据cron表达式得出时间now之后的下一次同步时间
     *
     * @param cronStr cron表达式
     * @param now     当前时间
     * @return
     * @throws ParseException cron表达式有误
     */
    public static Date nextValidTimeAfter(String cronStr, Date now) throws ParseException {
        CronExpression expression = new CronExpression(cronStr);
        Date nextValidTimeAfter = expression.getNextValidTimeAfter(now);
        return nextValidTimeAfter;
    }

    /**
     * 工具cron表达式算出时间段类需要同步的指标
     *
     * @param beginTime
     * @param endTime
     * @param cron
     * @return
     * @throws ParseException
     */
    public static List<Date> needSynchDateList(Date beginTime, Date endTime, String cron)
            throws Exception {
        List<Date> dateList = new ArrayList<Date>();
        Date nextDate = beginTime;
        while (true) {
            nextDate = nextValidTimeAfter(cron, nextDate);
            // nextDate 小于截止时间 而且小于当前系统时间
            if (nextDate.compareTo(endTime) <= 0 && nextDate.compareTo(new Date()) <= 0) {
                dateList.add(nextDate);
            } else {
                break;
            }
        }
        return dateList;
    }

    public static boolean isSatisfiedBy(String cron, Date date) throws ParseException {
        CronExpression expression = new CronExpression(cron);
        return expression.isSatisfiedBy(date);
    }

    /**
     * 字符串转换成date
     *
     * @param str 字符串
     * @return Date
     * @throws ParseException
     * @author jiaqi
     */
    public static Date string2Date(String str) throws ParseException {
        return string2Date(str, null);
    }

    /**
     * 字符串按照格式formater转换成date
     *
     * @param str    字符串
     * @param format 格式
     * @return
     * @throws ParseException
     * @author Administrator
     */
    public static Date string2Date(String str, String format) throws ParseException {
        if (format == null) {
            format = DEFAULT_DATETIME_FORMAT;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.parse(str);
    }

    /**
     * 获取指定时间的下一个月的1号 00:00:00
     *
     * @param date
     * @return
     * @author Administrator
     */
    public static Date nextMonthFirstDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 取系统今天的日期 小时及以下为0
     *
     * @return
     */
    public static Timestamp getTodayTimestamp() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTime().getTime());
    }

    /**
     * 取系统今天的日期 小时及以下为0
     *
     * @return
     */
    public static Long getTodaySeconds() {
        return getTodayTimestamp().getTime()/1000L;
    }

    /**
     * 取系统当前时间秒
     *
     * @return
     */
    public static Long getCurrentTimeSeconds() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime().getTime() / 1000L;
    }
    /**
     * 获取指定时间的下一个月的1号 00:00:00
     *
     * @param date
     * @return
     * @author Administrator
     */
    public static Date nextYearFirstDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static Date date2date(Date date, String format) {
        if (StringUtils.isEmpty(format)) {
            format = DEFAULT_DATETIME_FORMAT;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String str = sdf.format(date);
        try {
            date = sdf.parse(str);
        } catch (Exception e) {
            return null;
        }
        return date;
    }

    public static String date2string(Date date, String pattern) {
        if (StringUtils.isEmpty(pattern)) {
            pattern = DEFAULT_DATETIME_FORMAT;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static String date2string(Date date) {
        return date2string(date, DEFAULT_DATETIME_FORMAT);
    }

    public static Date millis2Date(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }

    public static Date seconds2Date(long seconds) {
        return millis2Date(seconds * 1000);
    }

    public static String seconds2DateStr(long seconds) {
        return seconds2DateStr(seconds, DEFAULT_DATETIME_FORMAT);
    }

    public static String seconds2DateStr(long seconds, String pattern) {
        return millis2DateStr(seconds * 1000,pattern);
    }

    /**
     * 毫秒转字符串形式日期
     *
     * @param millis
     * @return
     */
    public static String millis2DateStr(long millis) {
        return millis2DateStr(millis, DEFAULT_DATETIME_FORMAT);
    }

    public static String millis2DateStr(long millis, String pattern) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return date2string(calendar.getTime(), pattern);
    }
}
