package com.example.securenote.data.remote.translator

import com.example.securenote.data.remote.model.response.NewsResponseModel
import com.example.securenote.data.remote.model.response.RatingResponseModel
import com.example.securenote.domain.model.News
import com.example.securenote.domain.model.Rating

fun NewsResponseModel.toModel(): News {
    return News(
        id = id,
        title = title,
        price = price,
        description = description,
        category = category,
        image = image,
        rating = rating.toModel()
    )
}

fun RatingResponseModel.toModel(): Rating {
    return Rating(rate = rate, count = count)
}