package com.example.securenote.domain.enum


enum class DateRange(val value: Int, val label: String) {
    LAST_7_DAYS(0, "Last 7 days"),
    LAST_30_DAYS(1, "Last 30 days"),
    LAST_1_YEAR(2, "Last 1 year");

    companion object {
        fun fromValue(value: Int): DateRange {
            return entries.firstOrNull { it.value == value } ?: LAST_7_DAYS
        }
    }
}