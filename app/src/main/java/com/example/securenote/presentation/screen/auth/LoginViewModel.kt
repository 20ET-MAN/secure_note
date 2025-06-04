package com.example.securenote.presentation.screen.auth

import com.example.securenote.domain.repository.AppSettingsRepository
import com.example.securenote.presentation.base.BaseViewModel
import com.example.securenote.util.ErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    errorHandler: ErrorHandler,
    private val appSettingsRepository: AppSettingsRepository,
) : BaseViewModel(errorHandler) {

    private val _isDarkMode = MutableStateFlow<Boolean>(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        launchSilent {
            _isDarkMode.value = appSettingsRepository.isDarkMode.value
        }

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
