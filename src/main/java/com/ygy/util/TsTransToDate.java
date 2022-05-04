package com.ygy.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TsTransToDate {
    public static String transfer(Long times){
        Date date = new Date(times);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        System.out.println(dateString);
        return dateString;
    }
}
