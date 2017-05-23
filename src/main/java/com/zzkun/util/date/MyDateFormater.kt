package com.zzkun.util.date


import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


object MyDateFormater {

    private val f1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    //时间转为字符串
    fun toStr1(dateTime: LocalDateTime): String {
        return dateTime.format(f1)
    }

    fun toDT1(str: String): LocalDateTime {
        return LocalDateTime.parse(str.trim { it <= ' ' }, f1)
    }
}
