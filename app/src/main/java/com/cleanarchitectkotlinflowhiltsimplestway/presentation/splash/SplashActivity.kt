package com.cleanarchitectkotlinflowhiltsimplestway.presentation.splash

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.cleanarchitectkotlinflowhiltsimplestway.R.string.*
import com.cleanarchitectkotlinflowhiltsimplestway.databinding.SplashActivityBinding
import com.cleanarchitectkotlinflowhiltsimplestway.network.RequestState
import com.cleanarchitectkotlinflowhiltsimplestway.network.RequestState.*
import com.google.gson.JsonObject
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashActivityViewModel by viewModels()
    private lateinit var binding: SplashActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SplashActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lifecycleScope.launch {
            fetchSampleResponse()
        }
    }

    private suspend fun fetchSampleResponse() {
        delay(500)
        viewModel.getSampleResponse().collect {
            binding.contentField.applyNetworkResponse(it)
        }
    }

    private fun TextView.applyNetworkResponse(response: RequestState<JsonObject>) {
        text = when (response) {
            is Data -> getText(content_success).resolveData(response.data)
            is Error -> getText(content_error).resolveError(response.error)
            is Loading -> getText(content_loading)
        }
    }

    private fun CharSequence.resolveData(data: JsonObject) =
        toString().replace(getString(placeholder_data), data.toString())

    private fun CharSequence.resolveError(error: Throwable) =
        toString().replace(getString(placeholder_error), error.toString())

}