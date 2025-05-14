package com.example.securenote.presentation.screen

import com.example.securenote.domain.repository.AppLaunchRepository
import com.example.securenote.presentation.base.BaseViewModel
import com.example.securenote.util.ErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    errorHandler: ErrorHandler,
    private val appLaunchRepository: AppLaunchRepository
) : BaseViewModel(errorHandler) {

    private val _isFirstLaunch = MutableStateFlow<Boolean>(false)
    val isFirstLaunch: StateFlow<Boolean> = _isFirstLaunch


    init {
        launchInitialData {
            _isFirstLaunch.value = appLaunchRepository.isFirstLaunch()
        }
    }

}