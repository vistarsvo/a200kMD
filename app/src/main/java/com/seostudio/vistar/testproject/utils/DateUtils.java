package com.seostudio.vistar.testproject.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static String getCurrentDate(String txtFormat) {
        DateFormat dateFormat = new SimpleDateFormat(txtFormat);
        Date date = new Date();
        return dateFormat.format(date);
    }
}
