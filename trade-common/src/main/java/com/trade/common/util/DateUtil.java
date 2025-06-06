package com.trade.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 日期工具类
 * @author Trade Team
 */
public class DateUtil {

    /**
     * 将LocalDateTime格式化为指定字符串
     * @param dateTime LocalDateTime对象
     * @param pattern 格式模式，如 "yyyy-MM-dd HH:mm:ss"
     * @return 格式化后的日期字符串
     */
    public static String formatLocalDateTime(LocalDateTime dateTime, String pattern) {
        if (dateTime == null || pattern == null || pattern.isEmpty()) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return dateTime.format(formatter);
    }

    /**
     * 将当前LocalDateTime格式化为默认字符串 "yyyy-MM-dd HH:mm:ss"
     * @param dateTime LocalDateTime对象
     * @return 格式化后的日期字符串
     */
    public static String formatLocalDateTime(LocalDateTime dateTime) {
        return formatLocalDateTime(dateTime, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 将字符串解析为LocalDateTime对象
     * @param dateString 日期字符串
     * @param pattern 格式模式，如 "yyyy-MM-dd HH:mm:ss"
     * @return 解析后的LocalDateTime对象
     */
    public static LocalDateTime parseLocalDateTime(String dateString, String pattern) {
        if (dateString == null || dateString.isEmpty() || pattern == null || pattern.isEmpty()) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(dateString, formatter);
    }
}