package com.example.eShuttle.utils

import okhttp3.ResponseBody


//Handles  all types of responses
sealed class Resource<out T> {

    data class Success<out T>(val value: T) : Resource<T>()
    object Loading : Resource<Nothing>()

    data class Error(
        val isNetworkError: Boolean?,
        val errorCode: Int?,
        val errorBody: ResponseBody?

    ) : Resource<Nothing>()

}
