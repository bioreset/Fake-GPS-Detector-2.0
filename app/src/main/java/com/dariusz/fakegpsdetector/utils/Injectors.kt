package com.dariusz.fakegpsdetector.utils

import com.dariusz.fakegpsdetector.data.repository.LocationFromApiResponseRepository
import com.dariusz.fakegpsdetector.di.NetworkModule.provideRetrofitService
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
object Injectors {

    fun getLocationFromApiResponseRepository(): LocationFromApiResponseRepository {
        return LocationFromApiResponseRepository(
            provideRetrofitService()
        )
    }

}
