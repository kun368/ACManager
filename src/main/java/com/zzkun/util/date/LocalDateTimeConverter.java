package com.zzkun.util.date;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;

/**
 * Created by kun on 2016/9/16.
 */
public class LocalDateTimeConverter implements Converter<String, LocalDateTime> {
    @Override
    public LocalDateTime convert(String s) {
        return MyDateFormater.INSTANCE.toDT1(s);
    }
}
