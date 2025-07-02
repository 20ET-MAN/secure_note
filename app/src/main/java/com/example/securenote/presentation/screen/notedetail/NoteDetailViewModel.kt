package com.example.securenote.presentation.screen.notedetail

import androidx.lifecycle.SavedStateHandle
import com.example.securenote.domain.model.Note
import com.example.securenote.domain.model.NoteBlock
import com.example.securenote.domain.repository.NoteBlockRepository
import com.example.securenote.domain.repository.NoteRepository
import com.example.securenote.presentation.base.BaseViewModel
import com.example.securenote.util.ErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    errorHandler: ErrorHandler,
    val savedStateHandle: SavedStateHandle,
    private val noteRepository: NoteRepository,
    private val noteBlockRepository: NoteBlockRepository,
) :
    BaseViewModel(errorHandler) {

    var noteId: Long = -1L

    val isNewNote: Boolean
        get() = noteId == -1L

    private val _noteDetailUiState = MutableStateFlow(NoteDetailUiState())
    var noteDetailUiState: StateFlow<NoteDetailUiState> = _noteDetailUiState.asStateFlow()


    init {
        initNote()
    }

    private fun initNote() {
        launchSilent {
            noteId = savedStateHandle.get<Long>("noteId") ?: -1L
            val note: Note
            if (isNewNote) {
                val newNote = Note() // default empty
                val id = noteRepository.insertNote(newNote)
                note = newNote.copy(id = id)
            } else {
                note = noteRepository.getNote(noteId)
            }
            _noteDetailUiState.update { it.copy(note = note) }
            observeBlocks(note.id)
        }
    }

    private fun observeBlocks(noteId: Long) {
        launchSilent {
            noteBlockRepository.getBlocks(noteId).collect { blocks ->
                if (blocks.isEmpty()) {
                    insertBlock(NoteBlock(noteId = noteId))
                }
                _noteDetailUiState.update {
                    it.copy(noteBlock = blocks)
                }
            }
        }
    }

    fun insertBlock(block: NoteBlock) {
        launchSilent { noteBlockRepository.insertBlock(block) }
    }

    fun updateBlock(block: NoteBlock) {
        val updatedBlocks = _noteDetailUiState.value.noteBlock.map { current ->
            if (current.id == block.id) {
                current.copy(content = block.content)
            } else {
                current
            }
        }
        _noteDetailUiState.value = _noteDetailUiState.value.copy(noteBlock = updatedBlocks)
    }

    fun onNoteTitleChange(title: String) {
        _noteDetailUiState.update { it.copy(note = it.note.copy(title = title)) }
    }

    fun onSave() {
        launchSilent {
            _noteDetailUiState.value = _noteDetailUiState.value.copy(savedNote = false)
            noteRepository.updateNote(_noteDetailUiState.value.note.copy())
            _noteDetailUiState.value.noteBlock.forEach {
                noteBlockRepository.updateBlock(it)
            }
            _noteDetailUiState.value = _noteDetailUiState.value.copy(savedNote = true)
        }
    }

}