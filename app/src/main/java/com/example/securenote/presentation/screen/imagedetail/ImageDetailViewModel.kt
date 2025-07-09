package com.example.securenote.presentation.screen.imagedetail

import androidx.lifecycle.SavedStateHandle
import com.example.securenote.presentation.base.BaseViewModel
import com.example.securenote.util.ErrorHandler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class ImageDetailViewModel @Inject constructor(
    errorHandler: ErrorHandler,
    savedStateHandle: SavedStateHandle,
) :
    BaseViewModel(errorHandler) {
    val imagePaths: List<String> = savedStateHandle.get<String>("imagePaths")?.let { json ->
        Gson().fromJson(json, object : TypeToken<List<String>>() {}.type)
    } ?: emptyList()


    private var _currentImageSelected =
        MutableStateFlow<Int>(0)
    val currentImageSelected: StateFlow<Int> = _currentImageSelected.asStateFlow()

    init {
        _currentImageSelected.value = savedStateHandle.get<Int>("index") ?: 0
    }

    fun onPageChange(index: Int) {
        _currentImageSelected.value = index
    }


}