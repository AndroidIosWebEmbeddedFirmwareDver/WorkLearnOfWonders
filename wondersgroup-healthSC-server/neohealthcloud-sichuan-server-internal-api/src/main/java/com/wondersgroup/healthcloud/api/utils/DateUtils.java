/**
 *
 */
package com.wondersgroup.healthcloud.api.utils;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 *
 * @version 2014-4-15
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    public static String[] parsePatterns = {"yyyy-MM-dd",
            "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", "yyyy/MM/dd",
            "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM", "yyyy.MM.dd",
            "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd）
     */
    public static String getDate() {
        return getDate("yyyy-MM-dd");
    }

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String getDate(String pattern) {
        return DateFormatUtils.format(new Date(), pattern);
    }

    /**
     * 得到下个月日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String getNextMonthDate() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, 1);
        return DateFormatUtils.format(c.getTime(), "yyyy-MM-dd");
    }

    /**
     * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String formatDate(Date date, Object... pattern) {
        if (date == null) {
            return "";
        }
        String formatDate = null;
        if (pattern != null && pattern.length > 0) {
            formatDate = DateFormatUtils.format(date, pattern[0].toString());
        } else {
            formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
        }
        return formatDate;
    }

    /**
     * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String formatDateTime(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到当前时间字符串 格式（HH:mm:ss）
     */
    public static String getTime() {
        return formatDate(new Date(), "HH:mm:ss");
    }

    /**
     * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String getDateTime() {
        return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到当前年份字符串 格式（yyyy）
     */
    public static String getYear() {
        return formatDate(new Date(), "yyyy");
    }

    /**
     * 得到当前月份字符串 格式（MM）
     */
    public static String getMonth() {
        return formatDate(new Date(), "MM");
    }

    /**
     * 得到当天字符串 格式（dd）
     */
    public static String getDay() {
        return formatDate(new Date(), "dd");
    }

    public static int getCalender(Date date, int field) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (field == Calendar.MONTH)
            return cal.get(field) + 1;
        return cal.get(field);
    }


    /**
     * 得到当前星期字符串 格式（E）星期几
     */
    public static String getWeek() {
        return formatDate(new Date(), "E");
    }

    /**
     * 日期型字符串转化为日期 格式 { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
     * "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy.MM.dd",
     * "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String formatDate(String str) {
        SimpleDateFormat sf1 = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd");
        String sfstr = "";
        try {
            sfstr = sf2.format(sf1.parse(str));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sfstr;
    }

    /**
     * 获取过去的天数
     *
     * @param date
     * @return
     */
    public static long pastDays(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (24 * 60 * 60 * 1000);
    }

    public static Integer getAge(Date birthdate) {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthdate)) {
            return 0;
        }

        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthdate);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                age--;
            }
        }

        return age;
    }

    /**
     * 获取过去的小时
     *
     * @param date
     * @return
     */
    public static long pastHour(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (60 * 60 * 1000);
    }

    /**
     * 获取过去的分钟
     *
     * @param date
     * @return
     */
    public static long pastMinutes(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (60 * 1000);
    }

    /**
     * 转换为时间（天,时:分:秒.毫秒）
     *
     * @param timeMillis
     * @return
     */
    public static String formatDateTime(long timeMillis) {
        long day = timeMillis / (24 * 60 * 60 * 1000);
        long hour = (timeMillis / (60 * 60 * 1000) - day * 24);
        long min = ((timeMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (timeMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        long sss = (timeMillis - day * 24 * 60 * 60 * 1000 - hour * 60 * 60
                * 1000 - min * 60 * 1000 - s * 1000);
        return (day > 0 ? day + "," : "") + hour + ":" + min + ":" + s + "."
                + sss;
    }

    /**
     * 获取两个日期之间的天数
     *
     * @param before
     * @param after
     * @return
     */
    public static double getDistanceOfTwoDate(Date before, Date after) {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate) {
        smdate = parseDate(formatDate(smdate, "yyyy-MM-dd"));
        bdate = parseDate(formatDate(bdate, "yyyy-MM-dd"));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 比较时间大小
     *
     * @param sdate
     * @param edate
     * @return
     */
    public static String compareDays(Date sdate, Date edate) {

        long s = sdate.getTime() - new Date().getTime();
        long e = new Date().getTime() - edate.getTime();
        if (s > 0) {
            return "未开始";
        }
        if (e > 0) {
            return "已过期";
        }
        if (s <= 0 && e <= 0) {
            return "进行中";
        }
        return null;
    }

    /**
     * 比较两个日期的大小
     *
     * @param d1
     * @param d2
     * @return result>0 :d1晚于d2,result<0 :d1早于d2
     */
    public static int compare(Date d1, Date d2) {
        String str1 = formatDate(d1, "yyyy-MM-dd");
        String str2 = formatDate(d2, "yyyy-MM-dd");

        int result = str1.compareTo(str2);

        return result;
    }

    /**
     * 获取当前年度的第一天
     *
     * @param pattern
     * @return
     */
    public static String getCurrentYearFirstDate(String pattern) {
        return getYearFirstDate(Integer.valueOf(getYear()), pattern);
    }

    /**
     * 获取当前年度的最后一天
     *
     * @param pattern
     * @return
     */
    public static String getCurrentYearLastDate(String pattern) {
        return getYearLastDate(Integer.valueOf(getYear()), pattern);
    }

    /**
     * 获取某年度的第一天
     *
     * @param pattern
     * @return
     */
    public static String getYearFirstDate(int year, String pattern) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return DateFormatUtils.format(currYearFirst, pattern);
    }

    /**
     * 获取某年度的最后一天
     *
     * @param pattern
     * @return
     */
    public static String getYearLastDate(int year, String pattern) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();
        return DateFormatUtils.format(currYearLast, pattern);
    }

    /**
     * 获得指定日期的后一天
     *
     * @param
     * @returnT
     */
    public static Date getommorow(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date date1 = new Date(calendar.getTimeInMillis());
        return date1;
    }

    /**
     * 获得指定日期的后一年
     *
     * @param
     * @returnT
     */
    public static Date getNextYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, 1);
        Date date1 = new Date(calendar.getTimeInMillis());
        return date1;
    }

    /**
     * 获得指定日期的后几个月
     *
     * @param
     * @returnT
     */
    public static Date getNextMonth(Date date, int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, i);
        Date date1 = new Date(calendar.getTimeInMillis());
        return date1;
    }

    /**
     * 判断是否未超过一个月
     *
     * @param startDate
     * @return
     */
    public static boolean ifOneMonth(Date startDate) {
        long monthday;
        //开始时间与今天相比较
        Date endDate1 = new Date();

        Calendar starCal = Calendar.getInstance();
        starCal.setTime(startDate);

        int sYear = starCal.get(Calendar.YEAR);
        int sMonth = starCal.get(Calendar.MONTH);
        int sDay = starCal.get(Calendar.DATE);

        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate1);
        int eYear = endCal.get(Calendar.YEAR);
        int eMonth = endCal.get(Calendar.MONTH);
        int eDay = endCal.get(Calendar.DATE);

        monthday = ((eYear - sYear) * 12 + (eMonth - sMonth));

        if (sDay < eDay) {
            monthday = monthday + 1;
        }
        if (monthday <= 1) {
            return true;
        }

        return false;
    }

    /**
     * 计算开始日期和结束日期差
     *
     * @param startDate yyyy-MM-dd
     * @param endDate   yyyy-MM-dd
     * @return
     */
    private static int margin(String startDate, String endDate) {
        ParsePosition pos = new ParsePosition(0);
        ParsePosition pos2 = new ParsePosition(0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date ds = sdf.parse(startDate, pos);
        Date de = sdf.parse(endDate, pos2);
        long l = de.getTime() - ds.getTime();
        int margin = (int) (l / (24 * 60 * 60 * 1000));
        return margin;
    }


    public static boolean isValidDate(String str) {
        return isValidDate(str, null);
    }

    public static boolean isValidDate(String str, String pattern) {

        if (StringUtils.isBlank(pattern)) {
            pattern = "yyyy-MM-dd";
        }

        boolean convertSuccess = true;
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(str);
        } catch (ParseException e) {
            convertSuccess = false;
        }
        return convertSuccess;
    }
}
