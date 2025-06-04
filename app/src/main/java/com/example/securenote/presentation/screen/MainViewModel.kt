package com.example.securenote.presentation.screen

import com.example.securenote.domain.repository.AppSettingsRepository
import com.example.securenote.presentation.base.BaseViewModel
import com.example.securenote.util.ErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    errorHandler: ErrorHandler,
    private val appSettingsRepository: AppSettingsRepository,
) : BaseViewModel(errorHandler) {

    private val _isFirstLaunch = MutableStateFlow<Boolean>(false)
    val isFirstLaunch: StateFlow<Boolean> = _isFirstLaunch.asStateFlow()

    private val _isDarkMode = MutableStateFlow<Boolean>(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()


    init {
        loadData()
    }

    private fun loadData() {
        launchSilent {
            appSettingsRepository.isFirstLaunch().collect { iFl ->
                _isFirstLaunch.value = iFl
            }
        }

        launchSilent {
            appSettingsRepository.isDarkMode.collect { isDark ->
                _isDarkMode.value = isDark
            }
        }
    }
}

