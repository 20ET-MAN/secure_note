package com.example.securenote.presentation.navigation

sealed class SecureNoteDestination(val route: String) {
    object FirstLaunch : SecureNoteDestination("first_launch")
    object Login : SecureNoteDestination("login")
    object Home : SecureNoteDestination("home")
    object NoteDetail : SecureNoteDestination("note_detail/{noteId}") {
        fun createRoute(noteId: Int) = "note_detail/$noteId"
    }
    object Register : SecureNoteDestination("register")
    object ForgotPassword : SecureNoteDestination("forgot_password")
}