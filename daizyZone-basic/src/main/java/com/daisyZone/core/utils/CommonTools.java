package com.daisyZone.core.utils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;


/**
 * 公共方法工具类
 *
 * @author zhangyizhi
 */
public class CommonTools {

    private static String SOURCES = "0123456789";

    /**
     * 生成不重复的文件名
     *
     * @return
     */
    public static String generateFileName() {
        Calendar calendar = Calendar.getInstance();
        Random random = new Random(System.currentTimeMillis());

        String randomFileName = calendar.get(Calendar.YEAR) + "_" + (1 + calendar.get(Calendar.MONTH)) + "_"
                + calendar.get(Calendar.DAY_OF_MONTH) + "_" + calendar.get(Calendar.HOUR_OF_DAY) + "_"
                + calendar.get(Calendar.MINUTE) + "_" + calendar.get(Calendar.SECOND) + "_" + random.nextInt();

        return randomFileName;
    }

    final static class Helper {
        final ThreadLocal<java.text.NumberFormat> numberFormats = new ThreadLocal<java.text.NumberFormat>();

        final ThreadLocal<java.text.DateFormat> dateformats = new ThreadLocal<java.text.DateFormat>();

        java.text.DateFormat getDateformat() {
            java.text.DateFormat df = dateformats.get();
            if (df == null) {
                df = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                dateformats.set(df);
            }

            return df;
        }

        java.text.NumberFormat getNumberFormat() {
            java.text.NumberFormat nf = numberFormats.get();
            if (nf == null) {
                nf = java.text.NumberFormat.getInstance();
                nf.setMaximumFractionDigits(2);
                nf.setMinimumFractionDigits(2);
                nf.setGroupingUsed(false);
                numberFormats.set(nf);
            }

            return nf;
        }
    }

    final static Helper helper = new Helper();

    /**
     * 如果 theData 是null 返回null 否则是 theData.getBytes()
     *
     * @param theData
     * @return
     */
    public static byte[] getBytesValue(String theData) {
        if (theData != null)
            if (!"".equals(theData))
                return theData.getBytes();
        return null;
    }

    /**
     * 如果 theData 是null 返回null 否则是Byte.valueOf(theData).byteValue()
     *
     * @param theData
     * @return
     * @throws NumberFormatException
     */
    public static byte getByteValue(String theData) throws NumberFormatException {
        if (theData != null)
            if (!"".equals(theData))
                return Byte.valueOf(theData).byteValue();
        return 0;
    }

    /**
     * theData 是一个不完全的yyyy-MM-dd HH:mm:ss的日期字符串
     *
     * @param theData 格式为 yyyy-MM-dd HH:mm:ss的日期字符串
     * @return java.sql.Date
     */
    public static Date getDateValue(String theData) {
        Timestamp t = getTimestampValue(theData);
        if (t != null)
            return new Date(t.getTime());
        return null;
    }

    /**
     * 将util.Date 转换为 sql.Date
     *
     * @param theData
     * @return
     */
    public static Date getDateValue(Date theData) {
        if (theData != null)
            return new Date(theData.getTime());
        return null;
    }

    /**
     * theData 是一个不完全的yyyy-MM-dd HH:mm:ss的日期字符串
     *
     * @param theData 格式为 yyyy-MM-dd HH:mm:ss的日期字符串
     * @return java.util.Date
     */
    public static Date getUtilDateValue(String theData) {
        Timestamp t = getTimestampValue(theData);
        if (t != null)
            return new Date(t.getTime());
        return null;
    }

    /**
     * theData 如果 是 T TRUE Y YES 1(不分大小写) 返回true 否则为false;
     *
     * @param theData
     * @return
     * @throws NumberFormatException
     */
    public static boolean getBooleanValue(String theData) throws NumberFormatException {
        if (theData != null)
            if ("T".equalsIgnoreCase(theData) || "TRUE".equalsIgnoreCase(theData) || "Y".equalsIgnoreCase(theData)
                    || "YES".equalsIgnoreCase(theData) || "1".equalsIgnoreCase(theData))
                return true;
        return false;
    }

    /**
     * String to duble
     *
     * @param theData
     * @return
     * @throws NumberFormatException
     */
    public static double getDoubleValue(String theData) throws NumberFormatException {
        if (theData != null)
            if (!"".equals(theData))
                return Double.valueOf(theData).doubleValue();
        return 0;
    }

    /**
     * String to float
     *
     * @param theData
     * @return
     * @throws NumberFormatException
     */
    public static float getFloatValue(String theData) throws NumberFormatException {
        if (theData != null)
            if (!"".equals(theData))
                return Float.valueOf(theData).floatValue();
        return 0;
    }

    /**
     * String to int
     *
     * @param theData
     * @return
     * @throws NumberFormatException
     */
    public static int getIntValue(String theData) throws NumberFormatException {
        if (theData != null) {
            if (!"".equals(theData)) {
                try {
                    return Integer.valueOf(theData).intValue();
                } catch (NumberFormatException e) {
                    return Double.valueOf(theData).intValue();
                }
            }
        }
        return 0;
    }

    /**
     * String 2 long
     *
     * @param theData
     * @return
     * @throws NumberFormatException
     */
    public static long getLongValue(String theData) throws NumberFormatException {
        if (theData != null) {
            if (!"".equals(theData)) {
                try {
                    return Long.valueOf(theData).longValue();
                } catch (NumberFormatException e) {
                    return Double.valueOf(theData).longValue();
                }
            }
        }

        return 0;
    }

    /**
     * String to short
     *
     * @param theData
     * @return
     * @throws NumberFormatException
     */
    public static short getShortValue(String theData) throws NumberFormatException {
        if (theData != null)
            if (!"".equals(theData))
                return Short.valueOf(theData).shortValue();
        return 0;
    }

    /**
     * String to BigDecimal
     *
     * @param theData
     * @return
     */
    public static BigDecimal getBigDecimalValue(String theData) {
        if (theData == null || theData.length() == 0)
            return null;
        return new BigDecimal(theData);
    }

    /**
     * byte[] to String
     *
     * @param theData
     * @return
     */
    public static String getStringValue(byte[] theData) {
        if (theData != null)
            return Arrays.toString(theData);
        return "";
    }

    /**
     * byte to String
     *
     * @param theData
     * @return
     */
    public static String getStringValue(byte theData) {
        return String.valueOf(theData);
    }

    /**
     * BigDecimal to String
     *
     * @param theData
     * @return
     */
    public static String getStringValue(BigDecimal theData) {
        return helper.getNumberFormat().format(theData);
    }

    /**
     * double to String
     *
     * @param theData
     * @return
     */
    public static String getStringValue(double theData) {
        return helper.getNumberFormat().format(theData);
    }

    /**
     * float to String
     *
     * @param theData
     * @return
     */
    public static String getStringValue(float theData) {
        return helper.getNumberFormat().format(theData);
    }

    /**
     * int to String
     *
     * @param theData
     * @return
     */
    public static String getStringValue(int theData) {
        return String.valueOf(theData);
    }

    /**
     * long to String
     *
     * @param theData
     * @return
     */
    public static String getStringValue(long theData) {
        return String.valueOf(theData);
    }

    /**
     * String to String 非null的原样返回 null返回空字符串 这个可以避免打印出讨厌的null
     *
     * @param theData
     * @return
     */
    public static String getStringValue(String theData) {
        if (theData != null)
            return theData;
        return "";
    }

    /**
     * 取一个时间到指定Field的字符串
     *
     * @param theData
     * @param endField java.util.Calendar.SECOND java.util.Calendar.MINUTE
     *                 java.util.Calendar.HOUR_OF_DAY java.util.Calendar.DAY_OF_MONTH
     *                 <p>
     *                 myBatis 对于string 转 date 只技持MINUTE 和 DAY_OF_MONTH
     * @return null 将返回null
     */
    public static String getStringValue(Date theData, int endField) {
        Calendar calendar = Calendar.getInstance();
        if (theData == null)
            return null;
        calendar.setTime(theData);
        String returnVal = "";
        if (theData != null) {

            returnVal = helper.getDateformat().format(theData);
            int length = 19;
            switch (endField) {
                case Calendar.SECOND:
                    length = 19;
                    break;
                case Calendar.MINUTE:
                    length = 16;
                    break;
                case Calendar.HOUR_OF_DAY:
                    length = 13;
                    break;
                case Calendar.DAY_OF_MONTH:
                    length = 10;
                    break;
                default:
                    length = 10;
                    break;
            }
            returnVal = returnVal.substring(0, length);
        }

        return returnVal;
    }

    public static String getStringValue(java.sql.Date theData, int endField) {
        return getStringValue((Date) theData, endField);
    }

    /**
     * @param theData
     * @return
     * @see public static String getStringValue(java.util.Date theData)
     */
    public static String getStringValue(Timestamp theData, int endField) {
        return getStringValue((Date) theData, endField);
    }

    public static String getStringValue(String theData, int endField) {
        return getStringValue(getDateValue(theData), endField);
    }

    /**
     * Date to String 会将不必要的2005-01-01 00:00:00 00:00:00 这样的尾巴去除,看上去会可读性好些
     *
     * @param theData
     * @return 如果是null返回 空字符串 其他情况下会返回一个格式化的日期
     */
    public static String getStringValue(Date theData) {
        Calendar calendar = Calendar.getInstance();
        if (theData == null)
            return "";
        calendar.setTime(theData);
        String returnVal = "";
        if (theData != null) {
            boolean upDeleted = true;
            returnVal = helper.getDateformat().format(theData);

            if (upDeleted == true && calendar.get(Calendar.SECOND) == 0) {
                returnVal = returnVal.substring(0, 16);
            } else
                upDeleted = false;
            if (upDeleted == true && calendar.get(Calendar.MINUTE) == 0) {
                returnVal = returnVal.substring(0, 13);
            } else
                upDeleted = false;

            if (upDeleted == true && calendar.get(Calendar.HOUR_OF_DAY) == 0) {
                returnVal = returnVal.substring(0, 10);
            } else
                upDeleted = false;

            if (upDeleted == true && calendar.get(Calendar.DAY_OF_MONTH) == 0) {
                returnVal = returnVal.substring(0, 7);
            }
        }
        return returnVal;
    }

    /**
     * @param theData
     * @return
     * @see public static String getStringValue(java.util.Date theData)
     */
    public static String getStringValue(java.sql.Date theData) {
        return getStringValue((Date) theData);
    }

    /**
     * @param theData
     * @return
     * @see public static String getStringValue(java.util.Date theData)
     */
    public static String getStringValue(Timestamp theData) {
        return getStringValue((Date) theData);
    }

    /**
     * Object to String BigDecimal Timestamp java.util.Date 转调getStringValue 其他
     * toString
     *
     * @param theData
     * @return
     */
    public static String getStringValue(Object theData) {
        if (theData == null)
            return "";
        if (theData instanceof BigDecimal)
            return getStringValue((BigDecimal) theData);
        if (theData instanceof Timestamp)
            return getStringValue((Timestamp) theData);
        if (theData instanceof Date)
            return getStringValue((Date) theData);
        return theData.toString();
    }

    /**
     * String to Timestamp
     *
     * @param theData
     * @return
     * @throws IllegalArgumentException
     */
    public static Timestamp getTimestampValue(String theData) throws IllegalArgumentException {

        if (theData != null) {

            // 有些时候Extjs提交的时间格式是：2012-01-01T00:00:00
            theData = theData.replace('T', ' ');
            theData = theData.replace('t', ' ');
            if (!"".equals(theData)) {
                if (theData.length() == 4)
                    return Timestamp.valueOf(theData + "-01-01 00:00:00.000000000");
                if (theData.length() == 7)
                    return Timestamp.valueOf(theData + "-01 00:00:00.000000000");
                if (theData.length() == 10)
                    return Timestamp.valueOf(theData + " 00:00:00.000000000");
                if (theData.length() == 13)
                    return Timestamp.valueOf(theData + ":00:00.000000000");
                if (theData.length() == 16)
                    return Timestamp.valueOf(theData + ":00.000000000");
                if (theData.length() == 19)
                    return Timestamp.valueOf(theData + ".000000000");
                if (theData.length() > 19)
                    return Timestamp.valueOf(theData);
            }
        }
        return null;
    }

    /**
     * 返回一个时间 日期的后区间值 如果你查询一个日期 条件是 2012-01-01 到2012-01-31 可是数据库里2012-01-31这一天里
     * 2012-01-31 10:10:10的值直接比较就不包含在 2012-01-01 到2012-01-31区间里了
     * 可是大多数时候人们是表示的要2012-01-31这一天的也包含的 所以要把表示结束的查询条件改为 2012-01-31
     * 23:59:59.999999999
     */
    public static Timestamp getTimestampEndValue(String theData) throws IllegalArgumentException {
        if (theData != null)
            if (!"".equals(theData)) {

                if (theData.length() == 10)
                    return Timestamp.valueOf(theData + " 23:59:59.999999999");
                if (theData.length() == 13)
                    return Timestamp.valueOf(theData + ":59:59.999999999");
                if (theData.length() == 16)
                    return Timestamp.valueOf(theData + ":59.999999999");
                if (theData.length() == 19)
                    return Timestamp.valueOf(theData + ".999999999");
                if (theData.length() > 19)
                    return Timestamp.valueOf(theData);
            }
        return null;
    }

    /**
     * 将一般字符串中特别字符转为XML预先定义实体的参照 & amp; & &#38; & lt; < &#60; & gt; > &#62; &
     * apos; ' &#39; & quot; " &#34;
     *
     * @param str
     * @return
     */
    public static String encodeToXMLString(String str) {
        if (str == null)
            return "";
        if ("".equals(str))
            return "";

        StringBuffer tempstrbuf = new StringBuffer();
        char c;
        int i = 0;
        int length = str.length();
        for (i = 0; i < length; i++) {
            c = str.charAt(i);
            switch (c) {
                case '&':
                    tempstrbuf.append("&amp;");
                    break;
                case '\'':
                    tempstrbuf.append("&apos;");
                    break;
                case '\"':
                    tempstrbuf.append("&quot;");
                    break;
                case '<':
                    tempstrbuf.append("&lt;");
                    break;
                case '>':
                    tempstrbuf.append("&gt;");
                    break;
                default:
                    tempstrbuf.append(c);
            }
        }
        return tempstrbuf.toString();
    }

    /**
     * 将一个null或空字符串转为&nbsp; 在HTML中如果一个<TD></TD>间没有字符时显示不太好
     *
     * @param str
     * @return
     */
    public static String spaceHTMLEncode(String str) {
        if (str == null)
            return "&nbsp;";
        if ("".equals(str))
            return "&nbsp;";

        return str;
    }

    /**
     * 取一个字符串的前20个字符
     *
     * @param str
     * @return
     */
    public static String shortString(String str) {
        return shortString(str, 20);
    }

    /**
     * 取一个字符串的前length - 3个字符
     *
     * @param str
     * @param length 大于3以上才有意义
     * @return
     */
    public static String shortString(String str, int length) {
        if (str == null)
            return "";
        if (str.length() > length)
            return str.substring(0, length - 3) + "...";
        return str;
    }

    /**
     * 取一个double 类型的值小数点后两位的近似值的float值
     *
     * @param data
     * @return
     */
    public static float getNighFloat(double data) {
        BigDecimal Money = BigDecimal.valueOf(data);
        Money = Money.setScale(2, BigDecimal.ROUND_HALF_UP);
        return Money.floatValue();
    }

    /**
     * 测试一个数是不是近乎为0
     *
     * @param data
     * @return data < 0.000001 && data > -0.000001 就认为是近乎为0
     */
    public static boolean nighZero(double data) {
        if (data < 0.000001 && data > -0.000001)
            return true;
        else
            return false;
    }

    /**
     * 取系统当前这个月的开始日期
     *
     * @return
     */
    public static Timestamp getMonthTimestamp() {
        Timestamp returnVal;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        returnVal = new Timestamp(calendar.getTime().getTime());
        return returnVal;
    }

    /**
     * 取系统下一个月的开始日期
     *
     * @return
     */
    public static Timestamp getNextMonthTimestamp() {
        Timestamp returnVal;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
        returnVal = new Timestamp(calendar.getTime().getTime());
        return returnVal;
    }

    /**
     * 取系统上一个月的开始日期
     *
     * @return
     */
    public static Timestamp getBeforeMonthTimestamp() {
        Timestamp returnVal;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
        returnVal = new Timestamp(calendar.getTime().getTime());
        return returnVal;
    }

    /**
     * 取系统当前本年的开始日期
     *
     * @return
     */
    public static Timestamp getYearTimestamp() {
        Timestamp returnVal;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        returnVal = new Timestamp(calendar.getTime().getTime());
        return returnVal;
    }

    /**
     * 取系统下一年的开始日期
     *
     * @return
     */
    public static Timestamp getNextYearTimestamp() {
        Timestamp returnVal;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1);
        returnVal = new Timestamp(calendar.getTime().getTime());
        return returnVal;
    }

    /**
     * 取系统上一年的开始日期
     *
     * @return
     */
    public static Timestamp getBeforeYearTimestamp() {
        Timestamp returnVal;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 1);
        returnVal = new Timestamp(calendar.getTime().getTime());
        return returnVal;
    }

    /**
     * 取系统当前这个星期的开始日期
     *
     * @return
     */
    public static Timestamp getWeekTimestamp() {
        Timestamp returnVal;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.get(Calendar.DAY_OF_MONTH) + 1 - calendar.get(Calendar.DAY_OF_WEEK));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        returnVal = new Timestamp(calendar.getTime().getTime());
        return returnVal;
    }

    /**
     * 取系统下一个星期的开始日期
     *
     * @return
     */
    public static Timestamp getNextWeekTimestamp() {
        Timestamp returnVal;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.get(Calendar.DAY_OF_MONTH) + 8 - calendar.get(Calendar.DAY_OF_WEEK));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        returnVal = new Timestamp(calendar.getTime().getTime());
        return returnVal;
    }

    /**
     * 取系统上一个星期的开始日期
     *
     * @return
     */
    public static Timestamp getBeforeWeekTimestamp() {
        Timestamp returnVal;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.get(Calendar.DAY_OF_MONTH) + 1 - 7 - calendar.get(Calendar.DAY_OF_WEEK));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        returnVal = new Timestamp(calendar.getTime().getTime());
        return returnVal;
    }

    /**
     * 取系统今天的日期 小时及以下为0
     *
     * @return
     */
    public static Timestamp getTodayTimestamp() {
        Timestamp returnVal;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        returnVal = new Timestamp(calendar.getTime().getTime());
        return returnVal;
    }

    /**
     * 取系统明天的日期 小时及以下为0
     *
     * @return
     */
    public static Timestamp getTomorrowTimestamp() {
        Timestamp returnVal;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        returnVal = new Timestamp(calendar.getTime().getTime());
        return returnVal;
    }

    /**
     * 取系统当的的秒 毫秒及以下为0
     *
     * @return
     */
    public static Timestamp getSecondTimestamp() {
        Timestamp returnVal;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MILLISECOND, 0);
        returnVal = new Timestamp(calendar.getTime().getTime());
        return returnVal;
    }

    /**
     * 取系统当前的分钟 秒及以下为0
     *
     * @return
     */
    public static Timestamp getMinuteTimestamp() {
        Timestamp returnVal;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        returnVal = new Timestamp(calendar.getTime().getTime());
        return returnVal;
    }

    /**
     * 取系统当前的小时 分秒以下为0
     *
     * @return
     */
    public static Timestamp getHourTimestamp() {
        Timestamp returnVal;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        returnVal = new Timestamp(calendar.getTime().getTime());
        return returnVal;
    }

    private final static String[] hexDigits =
            {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * 转换字节数组为16进制字串
     *
     * @param b 字节数组
     * @return 16进制字串
     */
    public static String toHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(toHexString(b[i]));
        }
        return resultSb.toString();
    }

    /**
     * 将一人字节转换为一个十六进制的字符串
     *
     * @param b
     * @return
     */
    public static String toHexString(byte b) {
        int n = b;
        if (n < 0)
            n = 256 + n;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    /**
     * 将一个十六进制的字符串轩换为一个字节数组
     *
     * @param hexString
     * @return
     */
    public static byte[] toBytes(String hexString) {
        if (hexString == null)
            return null;
        if (hexString.length() == 0)
            return new byte[0];
        byte[] rtv = new byte[hexString.length() / 2 + hexString.length() % 2];
        byte one = 0;
        for (int i = 0; i < hexString.length(); i++) {
            if (i % 2 == 0) {
                one = Byte.parseByte(hexString.substring(i, i + 1), 16);
                rtv[i / 2] = one;
            } else {
                one = (byte) (16 * one + Byte.parseByte(hexString.substring(i, i + 1), 16));
                rtv[i / 2] = one;
            }
        }

        return rtv;
    }

    /**
     * 格式化全文件名 可以将一个包含路径字符的全文件名转换为一个可以在一个文件夹中存在的单纯文件名
     *
     * @param str
     * @return 格式化后的文件
     */
    public static String encodePathFileName(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }

        StringBuffer tempstrbuf = new StringBuffer();
        char c;
        int i = 0;
        int length = str.length();
        for (i = 0; i < length; i++) {
            c = str.charAt(i);
            switch (c) {
                // HTML定义
                case '&':
                    tempstrbuf.append("&amp;");
                    break;
                case '\"':
                    tempstrbuf.append("&quot;");
                    break;
                case '<':
                    tempstrbuf.append("&lt;");
                    break;
                case '>':
                    tempstrbuf.append("&gt;");
                    break;
                case ' ':
                    tempstrbuf.append("&nbsp;");
                    break;
                // 自定义 取HTML的&***;中间是这个字符的ASC码的十六进制表示
                case '\\':
                    tempstrbuf.append("&5C;");
                    break;
                case '/':
                    tempstrbuf.append("&2F;");
                    break;
                case ':':
                    tempstrbuf.append("&3A;");
                    break;
                case '*':
                    tempstrbuf.append("&2A;");
                    break;
                case '?':
                    tempstrbuf.append("&3F;");
                    break;
                case '|':
                    tempstrbuf.append("&7C;");
                    break;

                default:
                    tempstrbuf.append(c);
            }
        }
        return tempstrbuf.toString();
    }

    /**
     * 反格式化全文件名 是encodePathFileName的反转方法
     *
     * @param str
     * @return
     */
    public static String decodePathFileName(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        String sTemp;
        sTemp = str;
        // 自定义 取HTML的&***;中间是这个字符的ASC码的十六进制表示
        sTemp = sTemp.replaceAll("&5C;", "\\\\");
        sTemp = sTemp.replaceAll("&2F;", "/");
        sTemp = sTemp.replaceAll("&3A;", ":");
        sTemp = sTemp.replaceAll("&2A;", "*");
        sTemp = sTemp.replaceAll("&3F;", "?");
        sTemp = sTemp.replaceAll("&7C;", "|");

        // HTML定义
        sTemp = sTemp.replaceAll("&lt;", "<");
        sTemp = sTemp.replaceAll("&gt;", ">");
        sTemp = sTemp.replaceAll("&quot;", "\"");
        sTemp = sTemp.replaceAll("&nbsp;", " ");
        sTemp = sTemp.replaceAll("&amp;", "&");

        return sTemp;

    }

    /**
     * 编码文件夹 将文件夹中的 / 编码为 !
     * 将一个路径中的/ 转为! 这样子这个路径就可以在url中用到
     *
     * @param path
     * @return
     * @author wangyong
     */
    public static String encodePath(String path) {
        return path != null ? path.replace('/', '!') : path;
    }

    /**
     * 解码文件夹名  将文件夹名中的 ! 解码为  /
     *
     * @param urlPath
     * @return
     * @author wangyong
     */
    public static String decodePath(String urlPath) {
        return urlPath != null ? urlPath.replace('!', '/') : urlPath;
    }

    /**
     * E:\eclipse_wtp_1.5.4\configuration\file.txt return
     * [E:\eclipse_wtp_1.5.4\configuration\] E:\eclipse_wtp_1.5.4\configuration\
     * return [E:\eclipse_wtp_1.5.4\configuration\]
     *
     * @param fullFileName
     * @return
     */
    public static String getPath(String fullFileName) {
        if (fullFileName == null)
            return "";
        if (fullFileName.endsWith("\\") || fullFileName.endsWith("/"))
            return fullFileName;

        fullFileName = fullFileName.replace('\\', '/');
        while (fullFileName.indexOf("//") > 0) {
            fullFileName = fullFileName.replaceAll("//", "/");
        }
        int lastSpace = fullFileName.lastIndexOf('/');

        return fullFileName.substring(0, lastSpace + 1);
    }

    /**
     * E:\eclipse_wtp_1.5.4\configuration\file.txt return file.txt
     *
     * @param fileName
     * @return
     */
    public static String getBaseFileName(String fileName) {
        if (fileName == null)
            return "null";

        String fullFileName = fileName;

        /*
         * if (lastDot >= 0) fullFileName = fullFileName.substring(0, lastDot);
         */
        fullFileName = fullFileName.replace(':', '/');
        fullFileName = fullFileName.replace('\\', '/');
        while (fullFileName.indexOf("//") > 0) {
            fullFileName = fullFileName.replaceAll("//", "/");
        }
        int lastSpace = fullFileName.lastIndexOf('/');

        if (lastSpace >= 0)
            fullFileName = fullFileName.substring(lastSpace + 1);

        return fullFileName;
    }

    /**
     * file.txt return file
     *
     * @param fileName no path
     * @return
     */
    public static String getClearFileName(String fileName) {
        if (fileName == null)
            return "null";
        int lastDot = fileName.lastIndexOf(".");
        String noExtension = lastDot >= 0 ? fileName.substring(0, lastDot) : "";
        if (noExtension != null && noExtension.endsWith("."))
            noExtension = noExtension.substring(0, noExtension.length() - 1);
        return noExtension;
    }

    /**
     * 将两段文件路径连接起来 分隔符"/"
     *
     * @param parts1
     * @param parts2
     * @return
     */
    public static String buildupPath(String parts1, String parts2) {
        return buildupPath(new String[]
                {parts1, parts2});
    }

    /**
     * 将各段段文件路径连接起来 分隔符"/"
     *
     * @param parts
     * @return
     */
    public static String buildupPath(String[] parts) {
        if (parts == null || parts.length == 0)
            return "";
        String[] parts1 = new String[parts.length];
        for (int i = 0; i < parts.length; i++) {
            parts1[i] = parts[i] != null ? parts[i].replace('\\', '/') : "";
            if (parts1[i].startsWith("/") && i > 0)
                parts1[i] = parts1[i].substring(1);
            if (parts1[i].endsWith("/") && i < parts.length - 1)
                parts1[i] = parts1[i].substring(0, parts1[i].length() - 1);
        }

        String rtv = "";
        // parts[0] != null && parts[0].startsWith("/") ? "/" :

        for (int i = 0; i < parts1.length; i++) {
            rtv += (i == 0 ? "" : "/") + parts1[i];
        }

        return rtv;
    }

    /**
     * 将size 格式化为可读性好的 B KB MB ...
     *
     * @param size
     * @return
     */
    public static String getFormatSize(int size) {
        return getFormatSize((long) size);
    }

    /**
     * @param size
     * @return
     * @see public static String getFormatSize(int size)
     */
    public static String getFormatSize(long size) {
        String rtv = "";
        if (size > 0 && size < 1024)
            rtv = String.valueOf(size) + "B";
        else if (size > 1024 && size < 1024 * 1024)
            rtv = String.valueOf(size / 1024) + "KB";
        else if (size > 1024 * 1024 && size < 1024 * 1024 * 1024)
            rtv = String.valueOf(size / (1024 * 1024)) + "MB";
        return rtv;
    }

    /**
     * 用, ;分隔的二维字符数组的转义编码 各字符串之间用,号分隔 两个数组之间用;号分隔
     *
     * @param str
     * @return
     */
    public static String encodeStringArray(String[][] str) {
        if (str == null || str.length == 0) {
            return "";
        }

        StringBuffer tempstrbuf = new StringBuffer();
        int i = 0;
        int length = str.length;
        for (i = 0; i < length; i++) {
            if (i > 0)
                tempstrbuf.append(";");
            tempstrbuf.append(encodeStringArray(str[i]));
        }
        return tempstrbuf.toString();
    }

    /**
     * 用, ;分隔的二维字符数组的转义编码 各字符串之间用,号分隔
     *
     * @param str
     * @return
     */
    public static String encodeStringArray(String[] str) {
        if (str == null || str.length == 0) {
            return "";
        }

        StringBuffer tempstrbuf = new StringBuffer();
        int i = 0;
        int length = str.length;
        for (i = 0; i < length; i++) {
            if (i > 0)
                tempstrbuf.append(",");
            tempstrbuf.append(encodeStringArray(str[i]));
        }
        return tempstrbuf.toString();
    }

    /**
     * 用, ;分隔的二维字符数组的转义编码
     *
     * @param str
     * @return
     */
    public static String encodeStringArray(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }

        StringBuffer tempstrbuf = new StringBuffer();
        char c;
        int i = 0;
        int length = str.length();
        for (i = 0; i < length; i++) {
            c = str.charAt(i);
            switch (c) {

                // 自定义 取HTML的&***;中间是这个字符的ASC码的十六进制表示
                case ',':
                    tempstrbuf.append("%2C");
                    break;
                case ';':
                    tempstrbuf.append("%3B");
                    break;
                case '%':
                    tempstrbuf.append("%25");
                    break;
                default:
                    tempstrbuf.append(c);
            }
        }
        return tempstrbuf.toString();
    }

    /**
     * 用, ;分隔的二维字符数组的转义编码
     *
     * @param str
     * @return
     */
    public static String decodeString2String(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        String sTemp;
        sTemp = str;
        // 自定义 取HTML的&***;中间是这个字符的ASC码的十六进制表示
        sTemp = sTemp.replaceAll("%2C", ",");
        sTemp = sTemp.replaceAll("%3B", ";");
        sTemp = sTemp.replaceAll("%25", "%");
        return sTemp;
    }

    /**
     * 用, ;分隔的二维字符数组的转义编码
     *
     * @param str
     * @return
     */
    public static String[] decodeString2StringArray1(String str) {
        if (str == null || str.length() == 0) {
            return new String[0];
        }

        String[] rtv = split(str, ',');// str.split(",");
        for (int i = 0; i < rtv.length; i++) {
            rtv[i] = decodeString2String(rtv[i]);
        }

        return rtv;
    }

    /**
     * 用, ;分隔的二维字符数组的转义编码
     *
     * @param str
     * @return
     */
    public static String[][] decodeString2StringArray2(String str) {
        if (str == null || str.length() == 0) {
            return new String[0][];
        }
        String[] array1 = split(str, ';');// str.split(";");
        String[][] rtv = new String[array1.length][];
        for (int i = 0; i < array1.length; i++) {
            rtv[i] = decodeString2StringArray1(array1[i]);
        }

        return rtv;
    }

    /**
     * 分隔字符串
     *
     * @param str
     * @param c
     * @return
     */
    public static String[] split(String str, char c) {
        if (str == null || str.length() == 0)
            return new String[0];

        LinkedList<String> ls = new LinkedList<String>();
        int f = 0, t = 0;
        t = str.indexOf(c, f);
        String s = null;
        while (t != -1) {
            s = str.substring(f, t);

            ls.add(s);
            f = t + 1;
            t = str.indexOf(c, f);
        }
        s = str.substring(f);
        ls.add(s);

        String[] rtv = new String[ls.size()];
        if (ls.size() > 0)
            rtv = ls.toArray(rtv);

        return rtv;
    }

    /**
     * 判断字符串是否为空
     *
     * @param text 字符内容
     * @return boolean
     */
    public static boolean isNull(Object text) {
        if (text == null || "".equals(text)) {
            return true;
        } else {
            if ("".equals(text.toString().trim())) {
                return true;
            }
            return false;
        }
    }

    /**
     * 判断Long型字段是否为空或为0
     *
     * @param id Long型字段
     * @return 为空或为0则返回true，否则返回false
     */
    public static boolean isNull(Long id) {
        return id == null || id == 0L;
    }

    /**
     * 判断集合是否为空或没有包含对象
     *
     * @param collection 判断集合
     * @return boolean
     */
    public static boolean isNullOrEmpty(Collection<?> collection) {
        if (collection == null || collection.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取随机码<br>
     * UUID 随机码32位
     *
     * @return 生成的32位随机码
     * @author lindaixi
     */
    public static String generateHattedCode() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 生成随机数字字符串
     * @param size 字符串长度
     * @return
     */
    public static String generateSecurityCode(int size) {

        StringBuffer flag = new StringBuffer();
        for (int j = 0; j < size; j++) {
            flag.append(SOURCES.charAt(new Random().nextInt(9)));
        }
        return flag.toString();
    }
}
