package com.example.securenote.domain.repository

import com.example.securenote.domain.model.News

interface NewsRepository {
    suspend fun getNews(): List<News>
}