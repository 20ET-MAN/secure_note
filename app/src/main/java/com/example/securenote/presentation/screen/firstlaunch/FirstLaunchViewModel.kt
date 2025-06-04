package com.example.securenote.presentation.screen.firstlaunch

import com.example.securenote.domain.repository.AppSettingsRepository
import com.example.securenote.presentation.base.BaseViewModel
import com.example.securenote.util.ErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FirstLaunchViewModel @Inject constructor(
    private val appLaunchRepository: AppSettingsRepository,
    errorHandler: ErrorHandler
) : BaseViewModel(errorHandler = errorHandler) {

    suspend fun setFirstLaunchDone(){
        appLaunchRepository.setFirstLaunchDone()
    }
}