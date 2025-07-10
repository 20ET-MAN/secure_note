package com.example.securenote.data.remote.model.response

import com.google.gson.annotations.SerializedName

data class NewsResponseModel(
    @SerializedName("id")
    val id: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("price")
    val price: Double,
    @SerializedName("description")
    val description: String,
    @SerializedName("category")
    val category: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("rating")
    val rating: RatingResponseModel,
)


data class RatingResponseModel(
    @SerializedName("rate")
    val rate: Double,
    @SerializedName("count")
    val count: Int,
)