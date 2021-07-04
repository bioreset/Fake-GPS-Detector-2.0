package com.dariusz.fakegpsdetector.di

import com.dariusz.fakegpsdetector.di.NetworkModule.provideRetrofitService
import com.dariusz.fakegpsdetector.domain.repository.LocationFromApiResponseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.InternalCoroutinesApi

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @InternalCoroutinesApi
    @Provides
    fun getLocationFromApiResponseRepository(): LocationFromApiResponseRepository {
        return LocationFromApiResponseRepository(
            provideRetrofitService()
        )
    }

}