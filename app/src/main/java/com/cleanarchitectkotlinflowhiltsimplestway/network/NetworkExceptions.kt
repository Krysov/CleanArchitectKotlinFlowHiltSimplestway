package com.cleanarchitectkotlinflowhiltsimplestway.network

open class NetworkErrorException(
    val errorCode: Int = -1,
    private val errorMessage: String,
) : Exception() {

    override val message: String
        get() = localizedMessage

    override fun getLocalizedMessage() = errorMessage
}

class AuthenticationException(
    errorCode: Int = -1,
    authMessage: String,
) : NetworkErrorException(errorCode, authMessage)