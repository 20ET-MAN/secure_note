package com.example.securenote.domain.model

import java.time.LocalDate

data class ChartDataPoint(
    val date: LocalDate,
    val label: String,
    val value: Int
)