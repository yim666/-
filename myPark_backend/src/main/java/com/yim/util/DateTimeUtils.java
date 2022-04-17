package com.yim.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateTimeUtils {

    //设置时间显示格式
    private final static String COMMON_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private final static DateTimeFormatter COMMON_FORMATTER = DateTimeFormatter.ofPattern(COMMON_PATTERN);
//    设置时区
    private final static ZoneOffset DEFAULT_ZONE_OFFSET = ZoneOffset.of("+8");

    //    string to date
    private final static SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    /**
     * 默认 yyyy-MM-dd HH:mm:ss 格式
     */
    public static String dateToString(LocalDateTime dateTime) {
        assert dateTime != null;
        return COMMON_FORMATTER.format(dateTime);
    }

    public static Date dateToDate(LocalDateTime now) throws ParseException {
//        assert dateTime != null;
        String s = COMMON_FORMATTER.format(now);
        Date date = FORMATTER.parse(s);
        return date;
    }

    public static Date StringToDate(String s) throws ParseException {
        assert s != null;
        Date date = FORMATTER.parse(s);
        return date;
    }
    /**
     * 默认 yyyy-MM-dd HH:mm:ss 格式
     */
    public static LocalDateTime stringToDate(String dateStr) {
        assert dateStr != null;
        return LocalDateTime.parse(dateStr, COMMON_FORMATTER);
    }

    public static String dateToString(LocalDateTime dateTime, DateTimeFormatter formatter) {
        assert dateTime != null;
        return formatter.format(dateTime);
    }

    public static LocalDateTime stringToDate(String dateStr, DateTimeFormatter formatter) {
        assert dateStr != null;
        return LocalDateTime.parse(dateStr, formatter);
    }

    public static long dateToTimeMillis(LocalDateTime dateTime) {
        assert dateTime != null;
        return dateTime.toInstant(DEFAULT_ZONE_OFFSET).toEpochMilli();
    }

    public static LocalDateTime timeMillisToDate(long timeMillis) {
        Instant instant = Instant.ofEpochMilli(timeMillis);
        return LocalDateTime.ofInstant(instant, DEFAULT_ZONE_OFFSET);
    }

}
