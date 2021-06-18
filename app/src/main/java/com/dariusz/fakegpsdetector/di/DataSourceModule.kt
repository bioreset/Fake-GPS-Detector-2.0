package com.dariusz.fakegpsdetector.di

import android.content.Context
import com.dariusz.fakegpsdetector.data.source.local.sensor.celltowers.CellTowersLiveData
import com.dariusz.fakegpsdetector.data.source.local.sensor.location.LocationLiveData
import com.dariusz.fakegpsdetector.data.source.local.sensor.requirements.GpsStatusLiveData
import com.dariusz.fakegpsdetector.data.source.local.sensor.requirements.PermissionStatusLiveData
import com.dariusz.fakegpsdetector.data.source.local.sensor.requirements.WifiStatusLiveData
import com.dariusz.fakegpsdetector.data.source.local.sensor.wifinodes.WifiScanResultsLiveData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @Provides
    fun provideCellTowersLiveData(@ApplicationContext context: Context): CellTowersLiveData {
        return CellTowersLiveData(
            context
        )
    }

    @Singleton
    @Provides
    fun provideGpsStatusLiveData(@ApplicationContext context: Context): GpsStatusLiveData {
        return GpsStatusLiveData(context)
    }

    @Singleton
    @Provides
    fun provideLocationLiveData(@ApplicationContext context: Context): LocationLiveData {
        return LocationLiveData(
            context
        )
    }

    @Singleton
    @Provides
    fun providePermissionStatusLiveData(
        @ApplicationContext context: Context,
        permissionsToListen: List<String>
    ): PermissionStatusLiveData {
        return PermissionStatusLiveData(context, permissionsToListen)
    }

    @Singleton
    @Provides
    fun provideWifiScanResultsLiveData(@ApplicationContext context: Context): WifiScanResultsLiveData {
        return WifiScanResultsLiveData(
            context
        )
    }

    @Singleton
    @Provides
    fun provideWifiStatusLiveData(@ApplicationContext context: Context): WifiStatusLiveData {
        return WifiStatusLiveData(context)
    }
}
