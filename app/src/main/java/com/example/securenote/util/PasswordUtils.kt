package com.example.securenote.util

import java.security.MessageDigest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PasswordUtils @Inject constructor() {

    fun hashPassword(password: String): String {
        val messageDigest = MessageDigest.getInstance("SHA-256")
        val bytes = messageDigest.digest(password.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }

    fun verifyPassword(password: String, hash: String): Boolean {
        return hashPassword(password) == hash
    }
}