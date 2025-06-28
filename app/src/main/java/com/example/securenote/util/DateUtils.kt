package com.example.securenote.util

import java.text.SimpleDateFormat
import java.util.Locale

fun Long.toTimeDateString(): String {
    val dateTime = java.util.Date(this)
    val format = SimpleDateFormat("HH:mm:ss dd/MM/yyyy", Locale.US)
    return format.format(dateTime)
}