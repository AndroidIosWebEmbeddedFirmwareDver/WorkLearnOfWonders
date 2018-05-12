/**
 *
 */
package com.wondersgroup.hs.healthcloud.common.util;

import android.text.TextUtils;

import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * tanghaihua
 */
public class DateUtil {

    public static String DATE_FORMAT = "yyyy-MM-dd";
    public static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    static Calendar cal = Calendar.getInstance();

    private static SimpleDateFormat datetimeSdf;
    private static SimpleDateFormat dateSdf;
    private static SimpleDateFormat timeSdf;

    /**获取日期时间格式化*/
    public static SimpleDateFormat getDateTimeSdf() {
        if (datetimeSdf == null) {
            datetimeSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        }
        return datetimeSdf;
    }

    /** 获取日期格式化*/
    public static SimpleDateFormat getDateSdf() {
        if (dateSdf == null) {
            dateSdf = new SimpleDateFormat(DATE_FORMAT);
        }
        return dateSdf;
    }

    /** 获取时间格式化*/
    public static SimpleDateFormat getTimeSdf() {
        if (timeSdf == null) {
            timeSdf = new SimpleDateFormat("HH:mm");
        }
        return timeSdf;
    }

    public static Date alignDate(Date day) {
        if (day == null) return null;
        cal.setTime(day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        day = cal.getTime();
        return day;
    }

    public static int getInterDay(Date firstDay, Date secDay) {
        if (firstDay == null || secDay == null) return Integer.MAX_VALUE;
        cal.setTime(firstDay);
        int fd = cal.get(Calendar.DAY_OF_YEAR);
        cal.setTime(secDay);
        int sd = cal.get(Calendar.DAY_OF_YEAR);
        return fd - sd;
    }

    public static int getInterDay(String firstDayStr, String secDayStr) {
        if (TextUtils.isEmpty(firstDayStr) || TextUtils.isEmpty(secDayStr)) return Integer.MAX_VALUE;
        cal.setTime(parseDateByFormat(firstDayStr, DATE_FORMAT));
        int fd = cal.get(Calendar.DAY_OF_YEAR);
        cal.setTime(parseDateByFormat(secDayStr, DATE_FORMAT));
        int sd = cal.get(Calendar.DAY_OF_YEAR);
        return sd - fd;
    }

    public static Calendar getInterLaterDay(String startDateStr, int inter) {
        if (TextUtils.isEmpty(startDateStr)) return null;
        cal.setTime(parseDateByFormat(startDateStr, DATE_FORMAT));
        cal.add(Calendar.DAY_OF_MONTH, inter);
        return cal;
    }

    public static String getWeekOfCalendar(Calendar cal) {
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * 判断结束日期是否是开始日期的间隔日期
     *
     * @param start 开始日期
     * @param end 结束日期
     * @param unit
     * @param interval 间隔
     * @return
     */
    public static boolean isIntervalDate(Date start, Date end, String unit, int interval) {
        if (start.after(end)) {
            return false;
        }
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(start);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(end);

        int startYear = startCalendar.get(Calendar.YEAR);
        int startMonth = startCalendar.get(Calendar.MONTH) + 1;
        int startDay = startCalendar.get(Calendar.DAY_OF_MONTH);

        int endYear = endCalendar.get(Calendar.YEAR);
        int endMonth = endCalendar.get(Calendar.MONTH) + 1;
        int endDay = endCalendar.get(Calendar.DAY_OF_MONTH);
        // 结束日期当月最后一天
        int lastDay = endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        int diffMonth = (endYear - startYear) * 12 + (endMonth - startMonth);

        if ("month".equals(unit)) {
            if ((diffMonth % interval) == 0 && ((startDay == endDay) || (startDay > endDay && endDay == lastDay))) {
                return true;
            }
        } else if ("quarter".equals(unit)) {
            if ((diffMonth % (interval * 3)) == 0 && ((startDay == endDay) || (startDay > endDay && endDay == lastDay))) {
                return true;
            }
        } else if ("year".equals(unit)) {
            if ((diffMonth % (interval * 12)) == 0 && ((startDay == endDay) || (startDay > endDay && endDay == lastDay))) {
                return true;
            }
        } else if ("week".equals(unit)) {
            endCalendar.clear();
            endCalendar.set(endYear, endMonth - 1, endDay);
            startCalendar.clear();
            startCalendar.set(startYear, startMonth - 1, startDay);

            long diffDay = (endCalendar.getTimeInMillis() - startCalendar.getTimeInMillis()) / (1000 * 60 * 60 * 24);
            if ((diffDay % (interval * 7)) == 0) {
                return true;
            }
        }

        return false;
    }

    /**
     * 获得网络时间
     * @return
     */
    public static String getInternetDate() {
        try {
            URL url = new URL("http://www.baidu.com");
            URLConnection uc = url.openConnection();
            uc.connect();
            long ld = uc.getDate();
            return getDateSdf().format(new Date(ld));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将日期格式的字符串转换为长整型
     *
     * @param date
     * @param format
     * @return
     */
    public static long convert2long(String date, String format) {
        try {
            if (!TextUtils.isEmpty(date)) {
                if (TextUtils.isEmpty(format)) {
                    format = TIME_FORMAT;
                }
                SimpleDateFormat sf = new SimpleDateFormat(format);
                return sf.parse(date).getTime();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0l;
    }

    /**
     * 获得网络时间毫秒数
     * @return
     */
    public static long getInternetMillis() {
        try {
            URL url = new URL("http://www.baidu.com");
            URLConnection uc = url.openConnection();
            uc.connect();
            return uc.getDate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 转化格式 yyyy年MM月--->yyyy-MM
     *
     * @return
     */
    public static String stringFormat(String content) {
        DateFormat format1 = new SimpleDateFormat("yyyy年MM月");
        DateFormat format2 = new SimpleDateFormat("yyyy-MM");

        try {
            if (content != null && !"".equals(content)) {
                return format2.format(format1.parse(content));
            } else {
                return "";
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 按格式解析日期
     * @param date
     * @param format
     * @return
     */
    public static String parseDateByFormat(Date date, String format) {
        if(date == null){
            return null;
        }
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * 按格式解析日期
     * @param date
     * @param format
     * @return
     */
    public static Date parseDateByFormat(String date, String format) {
        if(TextUtils.isEmpty(date)){
            return null;
        }
        try {
            return new SimpleDateFormat(format).parse(date);
        } catch (ParseException e) {
        }
        return null;
    }

    /**
     * 获取今天 2016-06-16
     * @return
     */
    public static String getToday() {
        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);

        return year + "-" + (month < 10 ? ("0" + month) : month) + "-" + (day < 10 ? ("0" + day) : day);
    }
}
