package com.kongx.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 描述:公共日期工具类
 */
public class DateUtils {

    private static Logger logger = LoggerFactory.getLogger(DateUtils.class);


    public static final String DATE_FORMAT = "yyyy-MM-dd";

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_FORMAT_CHINESE = "yyyy年M月d日";

    public static final String FORMAT_HH_MM = "HH:mm";


    public static long dateDiffMinute(Date start, Date end) {
        long diff = end.getTime() - start.getTime();
        return diff / 60 / 1000;
    }

    /**
     * 将字符串日期转换为日期格式 自定義格式
     *
     * @param datestr
     * @return
     */
    public static Date stringToDate(String datestr, String dateformat) {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat(dateformat);
        try {
            date = df.parse(datestr);
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
        }
        return date;
    }

    /**
     * 将日期格式日期转换为字符串格式 自定義格式
     *
     * @param date
     * @param dateformat
     * @return
     */
    public static String dateToString(Date date, String dateformat) {
        if (date == null) {
            return "";
        }
        String datestr = null;
        SimpleDateFormat df = new SimpleDateFormat(dateformat);
        datestr = df.format(date);
        return datestr;
    }

    /**
     * 日期转换
     *
     * @param effectDateStr
     * @param pattern
     * @return
     * @throws ParseException
     */
    public static Date parseDate(String effectDateStr, String pattern) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date effectDate = simpleDateFormat.parse(effectDateStr);
        return effectDate;
    }


    public static String getDateDiff(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        StringBuilder result = new StringBuilder();
        if (day > 0) {
            result.append(day + "天");
        }
        if (hour > 0) {
            result.append(hour + "小时");
        }
        if (min > 0) {
            result.append(min + "分钟");
        }
        return result.toString();
    }

}