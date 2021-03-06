package com.bingyan.bytable.query;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by apple on 16/7/13.
 */
public class Config {
    public static final int QUERY_START_YEAR = 2017;
    public static final int QUERY_END_YEAR = 2017;
    public static final int QUERY_START_MONTH = Calendar.FEBRUARY;
    public static final int QUERY_END_MONTH = Calendar.AUGUST;
    public static final int QUERY_START_DAY = 25;
    public static final int QUERY_END_DAY = 11;
    public static final int NOTIFICATION_HOUR = 21;
    public static final int NOTIFICATION_MINUTE = 30;

    public static long getStartTimeInMillis(){
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.set(QUERY_START_YEAR,QUERY_START_MONTH,QUERY_START_DAY);
        return calendar.getTimeInMillis();
    }

    public static long getEndTimeInMillis(){
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.set(QUERY_END_YEAR,QUERY_END_MONTH,QUERY_END_DAY);
        return calendar.getTimeInMillis();
    }
}
