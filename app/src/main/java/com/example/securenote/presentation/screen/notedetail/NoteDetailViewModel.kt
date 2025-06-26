package com.example.securenote.presentation.screen.notedetail

import androidx.lifecycle.SavedStateHandle
import com.example.securenote.presentation.base.BaseViewModel
import com.example.securenote.util.ErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    errorHandler: ErrorHandler,
    savedStateHandle: SavedStateHandle,
) :
    BaseViewModel(errorHandler) {

    private val _noteId: String? = savedStateHandle.get<String>("noteId")

    private val _noteInput = MutableStateFlow("")
    var noteInput: StateFlow<String> = _noteInput.asStateFlow()

    init {
        Timber.d("Namnt:  argument = $_noteId")
    }


    fun onNoteValueChange(value: String) {
        _noteInput.value = value
    }
}