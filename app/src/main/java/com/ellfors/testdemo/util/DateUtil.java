package com.ellfors.testdemo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 时间工具类
 * 2018/3/26 18:00
 */
public class DateUtil
{
    //中间无分隔
    public static final String PATTERN_STANDARD06W = "yyyyMM";
    public static final String PATTERN_STANDARD08W = "yyyyMMdd";
    public static final String PATTERN_STANDARD12W = "yyyyMMddHHmm";
    public static final String PATTERN_STANDARD14W = "yyyyMMddHHmmss";
    public static final String PATTERN_STANDARD17W = "yyyyMMddHHmmssSSS";
    //横线
    public static final String PATTERN_STANDARD05H = "MM-dd";
    public static final String PATTERN_STANDARD07H = "yyyy-MM";
    public static final String PATTERN_STANDARD10H = "yyyy-MM-dd";
    public static final String PATTERN_STANDARD14H = "MM-dd HH:mm:ss";
    public static final String PATTERN_STANDARD16H = "yyyy-MM-dd HH:mm";
    public static final String PATTERN_STANDARD19H = "yyyy-MM-dd HH:mm:ss";
    //斜杠
    public static final String PATTERN_STANDARD10X = "yyyy/MM/dd";
    public static final String PATTERN_STANDARD16X = "yyyy/MM/dd HH:mm";
    public static final String PATTERN_STANDARD19X = "yyyy/MM/dd HH:mm:ss";
    //点
    public static final String PATTERN_STANDARD10D = "yyyy.MM.dd";
    public static final String PATTERN_STANDARD19D = "yyyy.MM.dd HH:mm:ss";
    //冒号
    public static final String PATTERN_STANDARD05M = "HH:mm";
    public static final String PATTERN_STANDARD10M = "HH:mm:ss:S";
    //中文
    public static final String PATTERN_STANDARD07ZN = "yyyy年MM月";

    /**
     * 日期格式的时间转化成字符串格式的时间
     */
    public static String date2String(Date date, String pattern)
    {
        if (date == null)
        {
            throw new java.lang.IllegalArgumentException("timestamp null illegal");
        }
        pattern = (pattern == null || pattern.equals("")) ? PATTERN_STANDARD19H : pattern;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        return sdf.format(date);
    }

    /**
     * 字符串格式的时间转化成日期格式的时间
     */
    public static Date string2Date(String strDate, String pattern)
    {
        if (strDate == null || strDate.equals(""))
        {
            throw new RuntimeException("strDate is null");
        }
        pattern = (pattern == null || pattern.equals("")) ? PATTERN_STANDARD19H : pattern;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = null;
        try
        {
            date = sdf.parse(strDate);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        return date;
    }

    /**
     * 字符串时间格式转换
     *
     * @param strDate   时间字符串
     * @param pattern   原格式
     * @param toPattern 转换后的格式
     */
    public static String string2String(String strDate, String pattern, String toPattern)
    {
        return date2String(string2Date(strDate, pattern), toPattern);
    }

    /**
     * 取得当前系统时间
     */
    public static String getCurrentTime(String format)
    {
        SimpleDateFormat formatDate = new SimpleDateFormat(format);
        String date = formatDate.format(new Date());
        return date;
    }

    /**
     * 获取想要的时间格式
     */
    public static String getWantDate(String dateStr, String wantFormat)
    {
        if (!"".equals(dateStr) && dateStr != null)
        {
            String pattern = PATTERN_STANDARD14W;
            int len = dateStr.length();
            switch (len)
            {
                case 8:
                    pattern = PATTERN_STANDARD08W;
                    break;
                case 12:
                    pattern = PATTERN_STANDARD12W;
                    break;
                case 14:
                    pattern = PATTERN_STANDARD14W;
                    break;
                case 17:
                    pattern = PATTERN_STANDARD17W;
                    break;
                case 10:
                    pattern = (dateStr.contains("-")) ? PATTERN_STANDARD10H : PATTERN_STANDARD10X;
                    break;
                case 16:
                    pattern = (dateStr.contains("-")) ? PATTERN_STANDARD16H : PATTERN_STANDARD16X;
                    break;
                case 19:
                    pattern = (dateStr.contains("-")) ? PATTERN_STANDARD19H : PATTERN_STANDARD19X;
                    break;
                default:
                    pattern = PATTERN_STANDARD14W;
                    break;
            }
            SimpleDateFormat sdf = new SimpleDateFormat(wantFormat);
            try
            {
                SimpleDateFormat sdfStr = new SimpleDateFormat(pattern);
                Date date = sdfStr.parse(dateStr);
                dateStr = sdf.format(date);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return dateStr;
    }

    /**
     * 获取该时间的几分钟之后的时间
     */
    public static String getAfterTime(String dateStr, int minute)
    {
        String returnStr = "";
        try
        {
            String pattern = PATTERN_STANDARD14W;
            int len = dateStr.length();
            switch (len)
            {
                case 8:
                    pattern = PATTERN_STANDARD08W;
                    break;
                case 10:
                    pattern = PATTERN_STANDARD10H;
                    break;
                case 12:
                    pattern = PATTERN_STANDARD12W;
                    break;
                case 14:
                    pattern = PATTERN_STANDARD14W;
                    break;
                case 16:
                    pattern = PATTERN_STANDARD16H;
                    break;
                case 17:
                    pattern = PATTERN_STANDARD17W;
                    break;
                case 19:
                    pattern = PATTERN_STANDARD19H;
                    break;
                default:
                    pattern = PATTERN_STANDARD14W;
                    break;
            }
            SimpleDateFormat formatDate = new SimpleDateFormat(pattern);
            Date date = null;
            date = formatDate.parse(dateStr);
            Date afterDate = new Date(date.getTime() + (60000 * minute));
            returnStr = formatDate.format(afterDate);
        }
        catch (Exception e)
        {
            returnStr = dateStr;
            e.printStackTrace();
        }
        return returnStr;
    }

    /**
     * 获取该时间的几分钟之前的时间
     */
    public static String getBeforeTime(String dateStr, int minute)
    {
        String returnStr = "";
        try
        {
            String pattern = PATTERN_STANDARD14W;
            int len = dateStr.length();
            switch (len)
            {
                case 8:
                    pattern = PATTERN_STANDARD08W;
                    break;
                case 10:
                    pattern = PATTERN_STANDARD10H;
                    break;
                case 12:
                    pattern = PATTERN_STANDARD12W;
                    break;
                case 14:
                    pattern = PATTERN_STANDARD14W;
                    break;
                case 16:
                    pattern = PATTERN_STANDARD16H;
                    break;
                case 17:
                    pattern = PATTERN_STANDARD17W;
                    break;
                case 19:
                    pattern = PATTERN_STANDARD19H;
                    break;
                default:
                    pattern = PATTERN_STANDARD14W;
                    break;
            }
            SimpleDateFormat formatDate = new SimpleDateFormat(pattern);
            Date date = null;
            date = formatDate.parse(dateStr);
            Date afterDate = new Date(date.getTime() - (60000 * minute));
            returnStr = formatDate.format(afterDate);
        }
        catch (Exception e)
        {
            returnStr = dateStr;
            e.printStackTrace();
        }
        return returnStr;
    }

    /**
     * 以友好的方式显示时间
     *
     * @param sdate
     * @return
     */
    public static String friendly_time(String sdate)
    {
        Date time = string2Date(sdate, PATTERN_STANDARD19H);
        if (time == null)
            return "";
        String ftime = "";
        Calendar cal = Calendar.getInstance();
        // 判断是否是同一天
        String curDate = date2String(cal.getTime(), PATTERN_STANDARD10H);
        String paramDate = date2String(time, PATTERN_STANDARD10H);
        //如果为同一天
        if (curDate.equals(paramDate))
        {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            int minute = (int) ((cal.getTimeInMillis() - time.getTime()) / 60000);
            if (hour == 0)
            {
                if (0 <= minute && minute <= 3)
                    ftime = "1分钟前";
                else if (3 < minute && minute <= 6)
                    ftime = "2分钟前";
                else if (6 < minute && minute <= 12)
                    ftime = "5分钟前";
                else if (12 < minute && minute <= 20)
                    ftime = "10分钟前";
                else if (20 < minute && minute <= 60)
                    ftime = "30分钟前";
            }
            else if (1 < hour && hour <= 2)
                ftime = "1小时前";
            else if (2 < hour && hour <= 4)
                ftime = "2小时前";
            else if (4 < hour && hour <= 8)
                ftime = "5小时前";
            else if (8 < hour && hour <= 16)
                ftime = "8小时前";
            else if (16 < hour && hour <= 24)
                ftime = "16小时前";
            return ftime;
        }
        long lt = time.getTime() / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        int days = (int) (ct - lt);
        if (days <= 0)
            ftime = date2String(time, PATTERN_STANDARD16H);
        else if (1 < days && days <= 2)
            ftime = "1天前";
        else if (2 < days && days <= 3)
            ftime = "2天前";
        else if (days > 3)
            ftime = date2String(time, PATTERN_STANDARD05H);
        else
            ftime = "";
        return ftime;
    }

    /**
     * 字符串日期转换成中文格式日期
     */
    public String dateToCnDate(String date)
    {
        String result = "";
        String[] cnDate = new String[]{"〇", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        String ten = "十";
        String[] dateStr = date.split("-");
        for (int i = 0; i < dateStr.length; i++)
        {
            for (int j = 0; j < dateStr[i].length(); j++)
            {
                String charStr = dateStr[i];
                String str = String.valueOf(charStr.charAt(j));
                if (charStr.length() == 2)
                {
                    if (charStr.equals("10"))
                    {
                        result += ten;
                        break;
                    }
                    else
                    {
                        if (j == 0)
                        {
                            if (charStr.charAt(j) == '1')
                                result += ten;
                            else if (charStr.charAt(j) == '0')
                                result += "";
                            else
                                result += cnDate[Integer.parseInt(str)] + ten;
                        }
                        if (j == 1)
                        {
                            if (charStr.charAt(j) == '0')
                                result += "";
                            else
                                result += cnDate[Integer.parseInt(str)];
                        }
                    }
                }
                else
                {
                    result += cnDate[Integer.parseInt(str)];
                }
            }
            if (i == 0)
            {
                result += "年";
                continue;
            }
            if (i == 1)
            {
                result += "月";
                continue;
            }
            if (i == 2)
            {
                result += "日";
                continue;
            }
        }
        return result;
    }

    /**
     * 将字符串转为时间戳
     */
    public static long getStringToDate(String dateString, String pattern)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = new Date();
        try
        {
            date = dateFormat.parse(dateString);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return date.getTime();
    }

    /**
     * 时间戳转换成字符窜
     */
    public static String getDateToString(long milSecond, String pattern)
    {
        Date date = new Date(milSecond);
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        format.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        return format.format(date);
    }

    /**
     * 时间戳转换成字符窜
     *
     * @param milSecond 时间
     * @param pattern   时间格式
     * @param id        格式化（GMT）(GMT+8:00)
     */
    public static String getDateToString(long milSecond, String pattern, String id)
    {
        Date date = new Date(milSecond);
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        format.setTimeZone(TimeZone.getTimeZone(id));
        return format.format(date);
    }

    /**
     * 是不是最近1天内
     */
    public static boolean isWithinOneDays(long targetTime)
    {
        long sevenDay = 24 * 3600 * 1000;
        long now = System.currentTimeMillis();
        return targetTime + sevenDay > now;
    }

    /**
     * 取前N个月的时间(当前月也算一个月)
     *
     * @param beforeMonth 距离的月份
     * @param pattern     格式化方式
     * @return String[0]N个月前的时间，String[1] 当前时间
     */
    public static String[] getN_MonthTime(int beforeMonth, String pattern)
    {
        Date dNow = new Date();
        Date dBefore;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dNow);
        calendar.add(Calendar.MONTH, -beforeMonth + 1);
        dBefore = calendar.getTime();
        String startDef = date2String(dBefore, pattern);
        String endDef = date2String(dNow, pattern);
        return new String[]{startDef, endDef};
    }
}
