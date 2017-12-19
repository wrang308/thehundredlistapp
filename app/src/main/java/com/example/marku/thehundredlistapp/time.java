package com.example.marku.thehundredlistapp;

/**
 * Created by marku on 2017-11-18.
 */

import java.util.Calendar;
import java.util.Date;
import java.util.function.LongToIntFunction;

import static java.lang.StrictMath.toIntExact;

public class time {

    public static int getDays() {
        int SECONDS_IN_A_DAY = 24 * 60 * 60;

        Calendar thatDay = Calendar.getInstance();
        thatDay.setTime(new Date(0)); /* reset */
        thatDay.set(Calendar.DAY_OF_MONTH, 2);
        thatDay.set(Calendar.MONTH, 6); // 0-11 so 1 less
        thatDay.set(Calendar.YEAR, 1994);

        Calendar today = Calendar.getInstance();
        long diff =  today.getTimeInMillis() - thatDay.getTimeInMillis();
        long diffSec = (diff / 1000) + 39600;
        long days = diffSec / SECONDS_IN_A_DAY;
        long secondsDay = diffSec % SECONDS_IN_A_DAY;
        long seconds = secondsDay % 60;
        long minutes = (secondsDay / 60) % 60;
        long hours = (secondsDay / 3600); // % 24 not needed

        // System.out.printf("%d days, %d hours, %d minutes and %d seconds\n", days, hours, minutes, seconds);

        return(toIntExact(days));




    }
}