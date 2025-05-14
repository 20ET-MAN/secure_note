package com.example.securenote.domain.entity

data class User(
    val id: String,
    val username: String,
    val email: String,
    val isBiometricEnabled: Boolean,
    val lastLoginTimestamp: Long
)
