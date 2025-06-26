package com.example.securenote.presentation.screen.setting

import com.example.securenote.domain.repository.AppSettingsRepository
import com.example.securenote.presentation.base.BaseViewModel
import com.example.securenote.util.ErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class SettingViewModel @Inject constructor(
    errorHandler: ErrorHandler,
    private val appSettingsRepository: AppSettingsRepository,
) :
    BaseViewModel(errorHandler) {

    private val _isDarkMode = MutableStateFlow<Boolean>(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        launchSilent {
            appSettingsRepository.isDarkMode.collect { isDark ->
                _isDarkMode.value = isDark
            }
        }
    }

    fun switchAppMode(value: Boolean) {
        launch {
            appSettingsRepository.switchUIMode(value)
        }
    }

}