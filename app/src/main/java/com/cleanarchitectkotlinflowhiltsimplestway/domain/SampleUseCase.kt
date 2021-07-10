package com.cleanarchitectkotlinflowhiltsimplestway.domain

import com.cleanarchitectkotlinflowhiltsimplestway.network.Api
import com.google.gson.JsonObject
import javax.inject.Inject

class SampleUseCase @Inject constructor(
    private val api: Api,
) {

    suspend operator fun invoke(): JsonObject {
        // here you can add some domain logic or call another UseCase
        return api.sampleGet()
    }
}