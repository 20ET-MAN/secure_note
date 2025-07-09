package com.example.securenote.presentation.screen.home

import com.example.securenote.domain.enum.DateRange
import com.example.securenote.domain.model.ChartDataPoint
import com.example.securenote.domain.model.Note

data class HomeUiState(
    val selectedTab: Int,
    val notes: List<Note> = emptyList<Note>(),
    val analyticLineChartData: List<ChartDataPoint> = emptyList<ChartDataPoint>(),
    val currentDateRange: DateRange = DateRange.LAST_7_DAYS
)
