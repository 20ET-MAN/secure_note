package com.example.securenote.data.service

import retrofit2.http.GET

interface SecureNoteService {
    @GET("sample-endpoint")
    suspend fun getSampleData(): Any
}
