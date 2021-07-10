package com.cleanarchitectkotlinflowhiltsimplestway.presentation.splash

import android.content.Context
import androidx.lifecycle.ViewModel
import com.cleanarchitectkotlinflowhiltsimplestway.domain.SampleUseCase
import com.cleanarchitectkotlinflowhiltsimplestway.network.RequestState.Data
import com.cleanarchitectkotlinflowhiltsimplestway.network.RequestState.Loading
import com.cleanarchitectkotlinflowhiltsimplestway.network.resolveNetworkError
import com.cleanarchitectkotlinflowhiltsimplestway.utils.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import java.lang.ref.WeakReference
import javax.inject.Inject

@HiltViewModel
class SplashActivityViewModel @Inject constructor(
    context: Context,
    private val sampleUseCase: SampleUseCase,
) : ViewModel() {

    private val contextRef = WeakReference(context)

    fun getSampleResponse() = flow {
        emit(Loading())
        try {
            delay(1000)
            emit(Data(sampleUseCase()))
        } catch (e: Exception) {
            Logger.e(e)
            contextRef.get()?.let {
                emit(it.resolveNetworkError(e))
            }
        }
    }
}