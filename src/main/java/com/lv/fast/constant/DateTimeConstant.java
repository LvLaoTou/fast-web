package com.lv.fast.constant;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * 日期时间常量类
 * @author jie.lv
 */
public class DateTimeConstant {

    private DateTimeConstant(){}

    /**
     * 日期格式化
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 时间格式化
     */
    public static final String TIME_FORMAT = "HH:mm:ss";

    /**
     * 时间格式化
     */
    public static final String DATE_TIME_FORMAT = DATE_FORMAT+" "+TIME_FORMAT;

    /**
     * 时区
     */
    public static final String TIME_ZONE = "GMT+8";

    /**
     * 时间格式化对象
     */
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter
            .ofPattern(DATE_TIME_FORMAT)
            .withZone(ZoneId.of(TIME_ZONE));
}
