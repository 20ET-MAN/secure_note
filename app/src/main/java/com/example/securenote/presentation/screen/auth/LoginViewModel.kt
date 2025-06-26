package com.example.securenote.presentation.screen.auth

import com.example.securenote.presentation.base.BaseViewModel
import com.example.securenote.util.ErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    errorHandler: ErrorHandler,
) : BaseViewModel(errorHandler) {

}
