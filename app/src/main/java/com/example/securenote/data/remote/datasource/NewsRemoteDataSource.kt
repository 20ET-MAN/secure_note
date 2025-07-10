package com.example.securenote.data.remote.datasource

import com.example.securenote.data.remote.model.response.NewsResponseModel

interface NewsRemoteDataSource {
    suspend fun getNews(): List<NewsResponseModel>
}