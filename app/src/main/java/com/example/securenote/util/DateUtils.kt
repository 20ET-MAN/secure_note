package com.example.securenote.util

import java.text.SimpleDateFormat
import java.util.Locale

fun Long.toTimeDateString(): String {
    val sdf = SimpleDateFormat(DateFormatType.DATE_TIME_FORMAT, Locale.getDefault())
    return sdf.format(java.util.Date(this))
}

fun String.convertDateToLong(): Long {
    return try {
        val df = SimpleDateFormat(DateFormatType.DATE_TIME_FORMAT, Locale.getDefault())
        df.parse(this)?.time ?: System.currentTimeMillis()
    } catch (e: Exception) {
        e.printStackTrace()
        System.currentTimeMillis()
    }
}

class DateFormatType{
    companion object{
        const val DATE_TIME_FORMAT ="yyyy.MM.dd HH:mm"
        const val YEAR_MONTH_DAY ="dd/MM/yyyy"
        const val MONTH_DAY ="dd/MM"
    }
}
