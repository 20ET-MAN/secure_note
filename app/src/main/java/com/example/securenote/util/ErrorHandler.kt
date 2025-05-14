package com.example.securenote.util


import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import okio.IOException
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErrorHandler @Inject constructor(@ApplicationContext private val context: Context) {

    fun getErrorMessage(throwable: Throwable): String {
        return when (throwable) {
            is HttpException -> {
                when (throwable.code()) {
                    400 -> "Bad Request"//context.getString(R.string.error_unknown)//
                    401 -> "Unauthorized"
                    403 -> "Forbidden"
                    404 -> "Not Found"
                    500 -> "Internal Server Error"
                    else -> "Network Error ${throwable.code()}"
                }
            }

            is UnknownHostException -> "No Internet Connection"
            is SocketTimeoutException -> "Connection Timeout"
            is IOException -> "Network Error"
            else -> throwable.message ?: "Unknown Error"
        }
    }
}