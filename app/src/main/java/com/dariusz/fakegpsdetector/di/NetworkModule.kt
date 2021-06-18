package com.dariusz.fakegpsdetector.di

import com.dariusz.fakegpsdetector.data.source.remote.api.FakeGPSRestApi
import com.dariusz.fakegpsdetector.data.source.remote.api.FakeGPSRestApiService
import com.dariusz.fakegpsdetector.data.source.remote.api.FakeGPSRestApiServiceImpl
import com.dariusz.fakegpsdetector.utils.Constants.API_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.InternalCoroutinesApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@InternalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): FakeGPSRestApi {
        val client = OkHttpClient.Builder().build()
        return Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(client)
            .build()
            .create(FakeGPSRestApi::class.java)
    }

    @Provides
    fun provideRetrofitService(): FakeGPSRestApiService {
        return FakeGPSRestApiServiceImpl()
    }
}
