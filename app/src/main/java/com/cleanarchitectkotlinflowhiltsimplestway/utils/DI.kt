package com.cleanarchitectkotlinflowhiltsimplestway.utils

import android.content.Context
import com.cleanarchitectkotlinflowhiltsimplestway.network.Api
import com.cleanarchitectkotlinflowhiltsimplestway.presentation.App
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    private val timeoutReadSeconds = 30
    private val timeoutWriteSeconds = 30
    private val timeoutConnectSeconds = 10
    private val sizeCacheBytes = 1048576L * 10 // 10 MB

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context) = app as App

    @Provides
    @Singleton
    fun provideContext(application: App): Context = application.applicationContext

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): Api = retrofit.create(Api::class.java)

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder().baseUrl(Constants.baseUrl).client(client)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(
        headerInterceptor: Interceptor,
        cache: Cache,
    ) = OkHttpClient().newBuilder()
        .connectTimeout(timeoutConnectSeconds.toLong(), TimeUnit.SECONDS)
        .readTimeout(timeoutReadSeconds.toLong(), TimeUnit.SECONDS)
        .writeTimeout(timeoutWriteSeconds.toLong(), TimeUnit.SECONDS)
        .cache(cache)
        .addInterceptor(headerInterceptor)
        .build()

    @Provides
    @Singleton
    fun provideHeaderInterceptor() = Interceptor {
        it.proceed(
            it.request().newBuilder()
                // call 'requestBuilder.addHeader(name, value)' to add request headers
                .build()
        )
    }

    @Provides
    @Singleton
    internal fun provideCache(context: Context) = Cache(
        directory = File(context.cacheDir.absolutePath, "HttpCache"),
        maxSize = sizeCacheBytes,
    )

}