package com.example.securenote.presentation.screen.home

import com.example.securenote.domain.enum.DateRange
import com.example.securenote.domain.model.LineChartDataPoint
import com.example.securenote.domain.model.Note
import com.example.securenote.domain.model.PieData

data class HomeUiState(
    val selectedTab: Int,
    val notes: List<Note> = emptyList<Note>(),
    val analyticLineChartData: List<LineChartDataPoint> = emptyList<LineChartDataPoint>(),
    val currentDateRange: DateRange = DateRange.LAST_7_DAYS,
    val pieChartData : List<PieData> = emptyList<PieData>()
)
