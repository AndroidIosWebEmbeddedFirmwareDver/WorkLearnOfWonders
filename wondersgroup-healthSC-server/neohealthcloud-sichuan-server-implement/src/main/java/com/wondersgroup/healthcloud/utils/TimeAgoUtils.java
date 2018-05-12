package com.wondersgroup.healthcloud.utils;


import java.util.Date;

import org.joda.time.DateTime;

/**
 * Created by zhangzhixiu on 15/2/7.
 */
public class TimeAgoUtils {
    private static final long minute = 60 * 1000;
    private static final long hour = 60 * minute;
    private static final long day = 24 * hour;
    private static final long week = 7 * day;

    /**
     * 得到"n天前"的字符串
     *
     * @param time
     * @return
     */
    public static String ago(Date time) {
        long now = System.currentTimeMillis();
        long t = time.getTime();
        long diff = now - t;
        if (diff < minute) {
            return "刚刚";
        } else if (minute <= diff && diff < hour) {
            return diff / minute + "分钟前";
        } else if (hour <= diff && diff < day) {
            return diff / hour + "小时前";
        } else {
            return diff / day + "天前";
        }
    }

    public static String assessAgo(Date time) {
        long now = System.currentTimeMillis();
        long t = time.getTime();
        long diff = now - t;
        if (diff < week) {
            if (isToday(time)) {
                return "今天";
            } else if (isYesterday(time)) {
                return "昨天";
            } else {
                int dayOfWeek = new DateTime(t).getDayOfWeek();
                switch (dayOfWeek) {
                    case 1:
                        return "星期一";
                    case 2:
                        return "星期二";
                    case 3:
                        return "星期三";
                    case 4:
                        return "星期四";
                    case 5:
                        return "星期五";
                    case 6:
                        return "星期六";
                    case 7:
                        return "星期日";
                }
            }
        }
        return new DateTime(time).toString("yy/MM/dd");
    }


    public static String measureAgo(Date time) {
        long now = System.currentTimeMillis();
        long t = time.getTime();
        long diff = now - t;
        if (isToday(time)) {
            return "今天";
        } else if (isYesterday(time)) {
            return "昨天";
        } else {
            return diff / day + "天前";
        }
    }

    private static Boolean isToday(Date time) {
        DateTime today = new DateTime(new DateTime(new Date()).toString("yyyy-MM-dd"));
        DateTime _time = new DateTime(new DateTime(time).toString("yyyy-MM-dd"));
        return today.compareTo(_time) == 0;
    }

    private static boolean isYesterday(Date time) {
        DateTime yesterday = new DateTime(new DateTime(new Date()).plusDays(-1).toString("yyyy-MM-dd"));
        DateTime _time = new DateTime(new DateTime(time).toString("yyyy-MM-dd"));
        return yesterday.compareTo(_time) == 0;
    }

    public static String questionAgo(Date time) {
        long now = System.currentTimeMillis();
        long t = time.getTime();
        long diff = now - t;
        if (diff < minute) {
            return "刚刚";
        } else if (minute <= diff && diff < hour) {
            return diff / minute + "分钟前";
        } else if (hour <= diff && diff < day) {
            return diff / hour + "小时前";
        } else if (day < diff && diff < 7 * day) {
            return diff / day + "天前";
        } else {
            return DateFormatter.dateTimeFormat(time);
        }
    }

    public static String highlight(Date time) {
        long now = System.currentTimeMillis();
        long t = time.getTime();
        long diff = now - t;
        if (diff < minute) {
            return "刚刚";
        } else if (minute <= diff && diff < hour) {
            return diff / minute + "分钟前";
        } else if (hour <= diff && diff < day) {
            return diff / hour + "小时前";
        } else if (day < diff && diff < 7 * day) {
            return diff / day + "天前";
        } else {
            return DateFormatter.monthDayFormat(time);
        }
    }

}
