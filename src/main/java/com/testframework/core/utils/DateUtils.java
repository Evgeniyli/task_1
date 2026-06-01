package com.testframework.core.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtils {
    public static final String YEAR = "yyyy";
    public static final String MONTH_NUMBER_FORM = "MM";
    public static final String DAY = "dd";
    public static final String EET_TIME_ZONE = "Europe/Kiev";
    public static final String YEAR_SHORT_FORM = "yy";

    /**
     * Get date and/or time in specified format and timezone
     *
     * @param timezone timezone
     * @param format   format
     * @return date and/or time in specified format
     */
    public static String getDateTime(String timezone, String format, Calendar calendar) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
        return dateFormat.format(calendar.getTime());
    }
}
