package com.example.securenote.presentation.screen.home

import com.example.securenote.domain.repository.NoteRepository
import com.example.securenote.presentation.base.BaseViewModel
import com.example.securenote.util.ErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class HomeViewModel @Inject constructor(
    errorHandler: ErrorHandler,
    private val noteRepository: NoteRepository,
) :
    BaseViewModel(errorHandler = errorHandler) {

    private val _homeUiState = MutableStateFlow(HomeUiState(selectedTab = 0))
    var homeUiState: StateFlow<HomeUiState> = _homeUiState.asStateFlow()


    init {
        launchSilent {
            noteRepository.getPrevNotes().collect {
                _homeUiState.value = _homeUiState.value.copy(notes = it)
            }
        }
    }


    fun onTabChange(pageIndex: Int) {
        _homeUiState.value = _homeUiState.value.copy(selectedTab = pageIndex)
    }

}