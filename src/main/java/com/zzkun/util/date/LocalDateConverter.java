package com.zzkun.util.date;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;

/**
 * Created by kun on 2016/9/16.
 */
public class LocalDateConverter implements Converter<String, LocalDate> {
    @Override
    public LocalDate convert(String s) {
        return LocalDate.parse(s);
    }
}
