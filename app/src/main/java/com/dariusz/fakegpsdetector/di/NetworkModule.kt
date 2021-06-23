package com.dariusz.fakegpsdetector.di

import com.dariusz.fakegpsdetector.data.source.remote.api.FakeGPSRestApi
import com.dariusz.fakegpsdetector.data.source.remote.api.FakeGPSRestApiService
import com.dariusz.fakegpsdetector.data.source.remote.api.FakeGPSRestApiServiceImpl
import com.dariusz.fakegpsdetector.utils.Constants.API_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewComponent
import kotlinx.coroutines.InternalCoroutinesApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

@InternalCoroutinesApi
@Module
@InstallIn(ViewComponent::class)
object NetworkModule {

    @Provides
    fun provideRetrofit(): FakeGPSRestApi {
        val client = OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC).setLevel
                    (HttpLoggingInterceptor.Level.BODY)
                    .setLevel(HttpLoggingInterceptor.Level.HEADERS)
            )
            .build()

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
