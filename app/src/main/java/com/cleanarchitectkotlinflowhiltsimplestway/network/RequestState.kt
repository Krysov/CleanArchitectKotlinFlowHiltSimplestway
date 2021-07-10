package com.cleanarchitectkotlinflowhiltsimplestway.network

sealed class RequestState<out T> {
    data class Loading(val progress: Float? = null) : RequestState<Nothing>()
    data class Error(val error: Throwable) : RequestState<Nothing>()
    data class Data<T>(val data: T) : RequestState<T>()
}