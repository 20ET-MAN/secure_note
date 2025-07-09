package com.example.securenote.domain.usecase

import com.example.securenote.domain.enum.BlockType
import com.example.securenote.domain.enum.DateRange
import com.example.securenote.domain.model.ChartDataPoint
import com.example.securenote.domain.repository.NoteBlockRepository
import com.example.securenote.util.DateFormatType
import com.example.securenote.util.convertDateToLong
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar

class GetBlocksByTimeUseCase @Inject constructor(private val noteBlockRepository: NoteBlockRepository) :
    BaseUseCase<GetBlocksByTimeUseCaseParams, Flow<List<ChartDataPoint>>>() {
    override suspend fun invoke(params: GetBlocksByTimeUseCaseParams): Flow<List<ChartDataPoint>> {
        val (startTime, endTime) = getTimeRange(params.timeRange)

        return noteBlockRepository.getBlocksByTime(startTime, endTime).map { blockList ->
            val grouped = blockList.groupBy { block ->
                val date = Instant.ofEpochMilli(block.createdAt.convertDateToLong())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()
                date
            }.mapValues { (_, blocksInDay) ->
                blocksInDay.sumOf { if (it.type == BlockType.IMAGE) 1 else it.content.length }
            }

            val startDate = Instant.ofEpochMilli(startTime)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
            val endDate = Instant.ofEpochMilli(endTime)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()

            val daysInRange = generateSequence(startDate) { current ->
                if (current.plusDays(1) <= endDate) current.plusDays(1) else null
            }.toList()

            daysInRange.map { date ->
                val value = grouped[date] ?: 0
                val label =
                    date.format(DateTimeFormatter.ofPattern(if (params.timeRange == DateRange.LAST_1_YEAR) DateFormatType.YEAR_MONTH_DAY else DateFormatType.MONTH_DAY))
                ChartDataPoint(date, label, value)
            }
        }
    }

    fun getTimeRange(dateRange: DateRange): Pair<Long, Long> {
        val endTime = System.currentTimeMillis()

        val calStart = Calendar.getInstance()
        calStart.timeInMillis = endTime

        when (dateRange) {
            DateRange.LAST_7_DAYS -> calStart.add(Calendar.DAY_OF_YEAR, -6)
            DateRange.LAST_30_DAYS -> calStart.add(Calendar.DAY_OF_YEAR, -29)
            DateRange.LAST_1_YEAR -> calStart.add(Calendar.YEAR, -1)
                .also { calStart.add(Calendar.DAY_OF_YEAR, 1) }
        }

        calStart.set(Calendar.HOUR_OF_DAY, 0)
        calStart.set(Calendar.MINUTE, 0)
        calStart.set(Calendar.SECOND, 0)
        calStart.set(Calendar.MILLISECOND, 0)

        val startTime = calStart.timeInMillis
        return Pair(startTime, endTime)
    }

}

data class GetBlocksByTimeUseCaseParams(
    val timeRange: DateRange,
)