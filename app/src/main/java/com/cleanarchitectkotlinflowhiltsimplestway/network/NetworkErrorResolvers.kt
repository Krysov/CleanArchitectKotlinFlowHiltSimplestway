package com.cleanarchitectkotlinflowhiltsimplestway.network

import android.content.Context
import com.cleanarchitectkotlinflowhiltsimplestway.R.string.*
import com.cleanarchitectkotlinflowhiltsimplestway.network.RequestState.Error
import org.json.JSONObject
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun Context.resolveNetworkError(e: Exception): Error {
    val resolvedExceptionBody = when (e) {
        is SocketTimeoutException ->
            NetworkErrorException(errorMessage = getString(content_error))
        is ConnectException ->
            NetworkErrorException(errorMessage = getString(error_no_internet))
        is UnknownHostException ->
            NetworkErrorException(errorMessage = getString(error_no_internet))
        is HttpException ->
            resolveHttpException(e)
        else -> e
    }
    return Error(resolvedExceptionBody)
}

private fun Context.resolveHttpException(e: HttpException) = when (e.code()) {
    400 -> parseHttpException(e)
    401 -> AuthenticationException(authMessage = getString(error_auth))
    502 -> NetworkErrorException(e.code(), errorMessage = getString(error_internal))
    else -> e
}

private fun Context.parseHttpException(e: HttpException): NetworkErrorException {
    return try {
        // here you can parse the error body that comes from server
        val errorBody = e.response()?.errorBody()?.string()
        NetworkErrorException(e.code(), JSONObject(errorBody!!).getString("message"))
    } catch (_: Exception) {
        NetworkErrorException(e.code(), errorMessage = getString(error_unexpected))
    }
}