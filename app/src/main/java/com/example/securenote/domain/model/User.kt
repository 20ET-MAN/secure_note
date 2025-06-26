package com.example.securenote.domain.model

data class User(
    val id: String,
    val username: String,
    val email: String,
    val isBiometricEnabled: Boolean,
    val lastLoginTimestamp: Long
)
