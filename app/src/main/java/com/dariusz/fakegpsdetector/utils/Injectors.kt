package com.dariusz.fakegpsdetector.utils

import android.content.Context
import com.dariusz.fakegpsdetector.data.repository.CellTowersRepository
import com.dariusz.fakegpsdetector.data.repository.LocationFromApiResponseRepository
import com.dariusz.fakegpsdetector.data.repository.LocationRepository
import com.dariusz.fakegpsdetector.data.repository.WifiNodesRepository
import com.dariusz.fakegpsdetector.di.CacheModule.provideCellTowersDAO
import com.dariusz.fakegpsdetector.di.CacheModule.provideLocationDAO
import com.dariusz.fakegpsdetector.di.CacheModule.provideLocationFromApiResponseDAO
import com.dariusz.fakegpsdetector.di.CacheModule.provideRoutersListDAO
import com.dariusz.fakegpsdetector.di.NetworkModule.provideRetrofitService
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
object Injectors {

    fun getCellTowersRepository(context: Context): CellTowersRepository {
        return CellTowersRepository(
            provideCellTowersDAO(context)
        )
    }

    fun getLocationFromApiResponseRepository(context: Context): LocationFromApiResponseRepository {
        return LocationFromApiResponseRepository(
            provideRetrofitService(),
            provideLocationFromApiResponseDAO(context)
        )
    }

    fun getLocationRepository(context: Context): LocationRepository {
        return LocationRepository(
            provideLocationDAO(context)
        )
    }

    fun getRoutersListRepository(context: Context): WifiNodesRepository {
        return WifiNodesRepository(
            provideRoutersListDAO(context)
        )
    }
}
