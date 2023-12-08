package com.example.agoshitask.common

sealed class Resource<T>(val data: T? = null, val errorMessage: String? = null) {
    class Success<T>(data: T?) : Resource<T>(data)
    class NoInternet<T>(data: T?) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(errorMessage: String) : Resource<T>(null, errorMessage)
}