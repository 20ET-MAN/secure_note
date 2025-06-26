package com.example.securenote.presentation.navigation

sealed class SecureNoteDestination(val route: String) {
    object FirstLaunch : SecureNoteDestination("first_launch")
    object Login : SecureNoteDestination("login")
    object Home : SecureNoteDestination("home")
    object NoteDetail : SecureNoteDestination("note_detail/{noteId}") {
        fun createRoute(noteId: String?) = "note_detail/$noteId"
    }

    object Setting : SecureNoteDestination("setting")
    object License : SecureNoteDestination("license")
}