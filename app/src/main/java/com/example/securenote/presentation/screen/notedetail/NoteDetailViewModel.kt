package com.example.securenote.presentation.screen.notedetail

import androidx.lifecycle.SavedStateHandle
import com.example.securenote.domain.enum.BlockType
import com.example.securenote.domain.enum.NoteType
import com.example.securenote.domain.model.Note
import com.example.securenote.domain.model.NoteBlock
import com.example.securenote.domain.repository.NoteBlockRepository
import com.example.securenote.domain.repository.NoteRepository
import com.example.securenote.presentation.base.BaseViewModel
import com.example.securenote.util.ErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    errorHandler: ErrorHandler,
    val savedStateHandle: SavedStateHandle,
    private val noteRepository: NoteRepository,
    private val noteBlockRepository: NoteBlockRepository,
) : BaseViewModel(errorHandler) {

    var noteId: Long = -1L

    val isNewNote: Boolean
        get() = noteId == -1L

    private val _noteDetailUiState = MutableStateFlow(NoteDetailUiState())
    var noteDetailUiState: StateFlow<NoteDetailUiState> = _noteDetailUiState.asStateFlow()

    private var observeNoteJob: Job? = null
    private var observeBlocksJob: Job? = null

    init {
        initNote()
    }

    private fun initNote() {
        launchSilent {
            noteId = savedStateHandle.get<Long>("noteId") ?: -1L
            if (isNewNote) {
                val newNote = Note() // default empty
                val id = noteRepository.insertNote(newNote)
                noteId = id
            }
            observeNote(noteId)
            observeBlocks(noteId)
        }
    }

    private fun observeBlocks(noteId: Long) {
        observeBlocksJob = launchSilent {
            noteBlockRepository.getBlocks(noteId).collect { blocks ->
                if (blocks.isEmpty() || blocks.lastOrNull()?.type == BlockType.IMAGE) {
                    insertBlock(NoteBlock(noteId = noteId))
                }
                _noteDetailUiState.update {
                    it.copy(noteBlock = blocks)
                }
                Timber.d("Namnt: noteBlock = ${_noteDetailUiState.value.noteBlock}")
                Timber.d("Namnt: note = ${_noteDetailUiState.value.note}")
            }

        }
    }

    private fun observeNote(id: Long) {
        observeNoteJob = launchSilent {
            noteRepository.getNote(id).collect { note ->
                if (note != null) {
                    _noteDetailUiState.update { it.copy(note = note) }
                }
            }
        }
    }

    fun insertBlock(block: NoteBlock) {
        launchSilent { noteBlockRepository.insertBlock(block) }
    }

    fun updateBlock(block: NoteBlock) {
        launchSilent {
            noteBlockRepository.updateBlock(block)
        }
    }

    fun onNoteTitleChange(title: String) {
        launchSilent {
            val newNote = _noteDetailUiState.value.note.copy(title = title)
            noteRepository.updateNote(newNote)
        }
    }

    fun onSave() {
        launchSilent {
            val isEmptyNote =
                (_noteDetailUiState.value.noteBlock.isEmpty() || _noteDetailUiState.value.noteBlock.all { it.content.isEmpty() }) && _noteDetailUiState.value.note.title.isEmpty()
            if (isEmptyNote) {
                _noteDetailUiState.value =
                    _noteDetailUiState.value.copy(noteErrorMessage = "Title hoặc content là mục bắt buộc! Click Back để thoát hoặc ấn hủy để tiêp tục")
            } else {
                _noteDetailUiState.value = _noteDetailUiState.value.copy(savedNote = false)
                noteRepository.updateNote(_noteDetailUiState.value.note)
                _noteDetailUiState.value.noteBlock.forEach {
                    noteBlockRepository.updateBlock(it)
                }
                _noteDetailUiState.value = _noteDetailUiState.value.copy(savedNote = true)
            }
        }
    }

    fun clearNoteErrorMessage() {
        _noteDetailUiState.value = _noteDetailUiState.value.copy(noteErrorMessage = null)
    }

    fun editBlockTypeImage(imgPaths: String) {
        launchSilent {
            _noteDetailUiState.update { it.copy(isAddImageDone = false) }
            val noteBlock = _noteDetailUiState.value.noteBlock
            if (noteBlock.isNotEmpty() && noteBlock.last().content.isNotEmpty()) {
                val block = NoteBlock(
                    type = BlockType.IMAGE,
                    content = imgPaths,
                    noteId = _noteDetailUiState.value.note.id
                )
                insertBlock(block)
            } else {
                val block = noteBlock.last().copy(type = BlockType.IMAGE, content = imgPaths)
                updateBlock(block = block)
            }
            _noteDetailUiState.update { it.copy(isAddImageDone = true) }

        }
    }

    fun deleteNote() {
        launchSilent {
            observeBlocksJob?.cancel()
            observeBlocksJob = null
            observeNoteJob?.cancel()
            observeNoteJob = null
            Timber.d("Namnt: NoteId = ${_noteDetailUiState.value.note.id} note = ${_noteDetailUiState.value.note}")
            noteRepository.deleteNote(_noteDetailUiState.value.note.id)
        }
    }

    fun onChangeTag(noteType: NoteType) {
        launchSilent {
            showSelectTagBtn(false)
            val newNote = _noteDetailUiState.value.note.copy(type = noteType)
            noteRepository.updateNote(newNote)
        }
    }

    fun showSelectTagBtn(isShow: Boolean) {
        _noteDetailUiState.update { it.copy(isOpenActionMenu = isShow) }
    }

}