package com.signme.signme.support;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by NooHeat on 06/10/2017.
 */

public class DateTime {
    public static String getDateNow() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String dateString = dateFormat.format(date); //2016-11-16 12:08:43

        return dateString;
    }
}
