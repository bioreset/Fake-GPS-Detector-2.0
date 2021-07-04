package com.dariusz.fakegpsdetector.di

import com.dariusz.fakegpsdetector.data.remote.api.FakeGPSRestApi
import com.dariusz.fakegpsdetector.data.remote.api.FakeGPSRestApiService
import com.dariusz.fakegpsdetector.data.remote.api.FakeGPSRestApiServiceImpl
import com.dariusz.fakegpsdetector.utils.Constants.API_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.InternalCoroutinesApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

@InternalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val logging: Interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
        level = HttpLoggingInterceptor.Level.BODY
        level = HttpLoggingInterceptor.Level.HEADERS
    }

    private val client = OkHttpClient.Builder()
        .addNetworkInterceptor(logging).build()

    private val moshi: Moshi =
        Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()

    @Provides
    fun provideRetrofit(): FakeGPSRestApi =
        Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .build()
            .create(FakeGPSRestApi::class.java)

    @Provides
    fun provideRetrofitService(): FakeGPSRestApiService {
        return FakeGPSRestApiServiceImpl()
    }
}
