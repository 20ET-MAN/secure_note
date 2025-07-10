package com.example.securenote.domain.model

data class News(
    val id: Long,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rating: Rating,
)


data class Rating(
    val rate: Double,
    val count: Int,
)