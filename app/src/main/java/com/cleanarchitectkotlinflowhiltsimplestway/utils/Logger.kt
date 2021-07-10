package com.cleanarchitectkotlinflowhiltsimplestway.utils

import androidx.viewbinding.BuildConfig

object Logger {

    private const val enabled = BuildConfig.DEBUG

    fun e(e: Exception) {
        if (enabled) e.printStackTrace()
    }

}