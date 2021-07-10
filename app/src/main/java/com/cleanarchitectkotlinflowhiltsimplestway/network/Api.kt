package com.cleanarchitectkotlinflowhiltsimplestway.network

import com.google.gson.JsonObject
import retrofit2.http.GET

interface Api {
    @GET("api/users")
    suspend fun sampleGet(): JsonObject
}