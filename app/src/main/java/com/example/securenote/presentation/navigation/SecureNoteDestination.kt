package com.example.securenote.presentation.navigation

import android.net.Uri
import com.google.gson.Gson

sealed class SecureNoteDestination(val route: String) {
    object FirstLaunch : SecureNoteDestination("first_launch")
    object Login : SecureNoteDestination("login")
    object Home : SecureNoteDestination("home")
    object NoteDetail : SecureNoteDestination("note_detail/{noteId}") {
        fun createRoute(noteId: Long) = "note_detail/$noteId"
    }

    object Setting : SecureNoteDestination("setting")
    object License : SecureNoteDestination("license")
    object ImageDetail : SecureNoteDestination("image_detail?imagePaths={imagePaths}&index={index}") {
        fun createRoute(imagePaths: List<String>, index: Int): String {
            val json = Uri.encode(Gson().toJson(imagePaths))
            return "image_detail?imagePaths=$json&index=$index"
        }
    }
}