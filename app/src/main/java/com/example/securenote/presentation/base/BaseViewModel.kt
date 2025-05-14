package com.example.securenote.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.securenote.util.ErrorHandler
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel(protected val errorHandler: ErrorHandler) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _pageStatus = MutableStateFlow<PageStatus>(PageStatus.LOADED)
    val pageStatus: StateFlow<PageStatus> = _pageStatus.asStateFlow()

    private var lastRetryBlock: (suspend CoroutineScope.() -> Unit)? = null

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        handleException(throwable)
    }

    protected fun launchInitialData(
        block: suspend CoroutineScope.() -> Unit
    ) {
        lastRetryBlock = block
        _pageStatus.value = PageStatus.INITIAL
        _error.value = null

        viewModelScope.launch(exceptionHandler) {
            block()
            _pageStatus.value = PageStatus.LOADED
        }
    }

    protected fun launch(
        block: suspend CoroutineScope.() -> Unit
    ) {
        lastRetryBlock = block
        _error.value = null
        _isLoading.value = true

        viewModelScope.launch(exceptionHandler) {
            block()
            _isLoading.value = false
        }
    }

    open fun retry() {
        lastRetryBlock?.let { block ->
            if (_pageStatus.value == PageStatus.INITIAL_ERROR) {
                launchInitialData { block() }
            } else {
                launch { block() }
            }
        }
    }

    fun clearError() {
        _error.value = null
    }

    private fun handleException(throwable: Throwable) {
        val errorMessage = errorHandler.getErrorMessage(throwable)
        _error.value = errorMessage

        if (_pageStatus.value == PageStatus.INITIAL) {
            _pageStatus.value = PageStatus.INITIAL_ERROR
        }

        _isLoading.value = false
    }

    open fun handlerError(value: String) {
        _error.value = value
    }
}