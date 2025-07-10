package com.example.securenote.data.remote.datasourceimpl

import com.example.securenote.data.remote.datasource.NewsRemoteDataSource
import com.example.securenote.data.remote.model.response.NewsResponseModel
import com.example.securenote.data.service.SecureNoteService
import jakarta.inject.Inject

class NewRemoteDataSourceImpl@Inject constructor(private val secureNoteService: SecureNoteService): NewsRemoteDataSource {
    override suspend fun getNews(): List<NewsResponseModel> {
        return secureNoteService.getNews()
    }
}