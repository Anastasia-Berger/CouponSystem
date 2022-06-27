package com.jb.CouponSystem.utils;

import java.time.LocalDateTime;

/**
 * Created by kobis on 02 Apr, 2022
 */
public class Date {

    public static String format(LocalDateTime ldt){

        int day = ldt.getDayOfMonth();
        int month = ldt.getMonthValue();
        int year = ldt.getYear();

        int hour = ldt.getHour();
        int minute = ldt.getMinute();
        return String.format("%02d/%02d/%04d @ %02d:%02d",day,month,year,hour,minute);
    }
}
