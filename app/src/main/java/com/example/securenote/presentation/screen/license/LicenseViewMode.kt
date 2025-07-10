package com.example.securenote.presentation.screen.license

import com.example.securenote.presentation.base.BaseViewModel
import com.example.securenote.util.ErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class LicenseViewMode @Inject constructor(errorHandler: ErrorHandler) :
    BaseViewModel(errorHandler) {
}