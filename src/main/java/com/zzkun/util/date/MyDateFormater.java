package com.zzkun.util.date;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by kun on 2016/8/17.
 */
public class MyDateFormater {

    private static final DateTimeFormatter f1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    //时间转为字符串
    public static String toStr1(LocalDateTime dateTime) {
        return dateTime.format(f1);
    }

    public static LocalDateTime toDT1(String str) {
        return LocalDateTime.parse(str, f1);
    }
}
