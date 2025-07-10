package com.example.securenote.data.remote.model.request

import com.google.gson.annotations.SerializedName

data class NewsRequestModel(
    @SerializedName("source-country")
    val sourceCountry: String,
    @SerializedName("date")
    val date: String,
)
