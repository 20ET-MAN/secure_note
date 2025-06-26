package com.example.securenote.presentation.screen.home

import com.example.securenote.presentation.base.BaseViewModel
import com.example.securenote.util.ErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class HomeViewModel @Inject constructor(errorHandler: ErrorHandler) :
    BaseViewModel(errorHandler = errorHandler) {

    private val _selectedTab = MutableStateFlow(0)
    var selectedTab: StateFlow<Int> = _selectedTab.asStateFlow()


    fun onTabChange(pageIndex: Int) {
        _selectedTab.value = pageIndex
    }

}