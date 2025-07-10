package com.example.securenote.data

import com.example.securenote.data.remote.datasource.NewsRemoteDataSource
import com.example.securenote.data.remote.translator.toModel
import com.example.securenote.domain.model.News
import com.example.securenote.domain.repository.NewsRepository
import jakarta.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val newsRemoteDataSource: NewsRemoteDataSource) :
    NewsRepository {
    override suspend fun getNews(): List<News> {
        return newsRemoteDataSource.getNews().map { it.toModel() }
    }
}