package com.example.securenote.presentation.screen.home

import com.example.securenote.domain.enum.DateRange
import com.example.securenote.domain.repository.NoteRepository
import com.example.securenote.domain.usecase.GetBlocksByTimeUseCase
import com.example.securenote.domain.usecase.GetBlocksByTimeUseCaseParams
import com.example.securenote.presentation.base.BaseViewModel
import com.example.securenote.util.ErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class HomeViewModel @Inject constructor(
    errorHandler: ErrorHandler,
    private val noteRepository: NoteRepository,
    private val getBlocksByTimeUseCase: GetBlocksByTimeUseCase,
) :
    BaseViewModel(errorHandler = errorHandler) {

    private val _homeUiState = MutableStateFlow(HomeUiState(selectedTab = 0))
    var homeUiState: StateFlow<HomeUiState> = _homeUiState.asStateFlow()

    init {
        launchSilent {
            noteRepository.getPrevNotes().collect { notes ->
                _homeUiState.update {
                    it.copy(notes = notes)
                }
            }
        }
        loadLineChartData()

    }

    fun loadLineChartData(dateRange: DateRange = DateRange.LAST_7_DAYS) {
        launchSilent {
            getBlocksByTimeUseCase.invoke(
                GetBlocksByTimeUseCaseParams(
                    dateRange
                )
            )
                .collect { analyticValue ->
                    _homeUiState.update {
                        it.copy(analyticLineChartData = analyticValue, currentDateRange = dateRange)
                    }
                }
        }
    }


    fun onTabChange(pageIndex: Int) {
        _homeUiState.value = _homeUiState.value.copy(selectedTab = pageIndex)
    }


}