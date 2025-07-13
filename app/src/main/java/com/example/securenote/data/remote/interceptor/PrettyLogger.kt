package com.example.securenote.data.remote.interceptor

import android.util.Log
import com.google.gson.GsonBuilder
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber

class PrettyLogger : HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        if (message.startsWith("{") || message.startsWith("[")) {
            try {
                val gson = GsonBuilder().setPrettyPrinting().create()
                val jsonElement = com.google.gson.JsonParser.parseString(message)
                val prettyJson = gson.toJson(jsonElement)
                Timber.log(Log.DEBUG, prettyJson)
            } catch (e: Exception) {
                Timber.log(Log.ERROR,  e)
            }
        } else {
            Timber.log(Log.DEBUG, "API_RESPONSE $message")
        }
    }
}