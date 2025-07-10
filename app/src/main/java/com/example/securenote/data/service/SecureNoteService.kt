package com.example.securenote.data.service

import com.example.securenote.data.remote.model.response.NewsResponseModel
import retrofit2.http.GET

interface SecureNoteService {

    @GET("https://fakestoreapi.com/products")
    suspend fun getNews(): List<NewsResponseModel>
}
