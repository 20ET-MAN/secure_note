package com.example.securenote.presentation.screen.home

import com.example.securenote.presentation.base.BaseViewModel
import com.example.securenote.util.ErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.delay

@HiltViewModel
class HomeViewModel @Inject constructor(errorHandler: ErrorHandler) :
    BaseViewModel(errorHandler = errorHandler) {


    init {
        launchInitialData {
            delay(5000)
        }
    }

}