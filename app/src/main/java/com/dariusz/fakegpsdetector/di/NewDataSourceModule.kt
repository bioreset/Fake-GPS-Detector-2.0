package com.dariusz.fakegpsdetector.di

import android.content.Context
import com.dariusz.fakegpsdetector.data.source.local.sensor.celltowers.CellTowersData
import com.dariusz.fakegpsdetector.data.source.local.sensor.location.LocationData
import com.dariusz.fakegpsdetector.data.source.local.sensor.requirements.GpsStatusData
import com.dariusz.fakegpsdetector.data.source.local.sensor.requirements.PermissionsStatusData
import com.dariusz.fakegpsdetector.data.source.local.sensor.requirements.WifiStatusData
import com.dariusz.fakegpsdetector.data.source.local.sensor.wifinodes.WifiScanResultsData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
object NewDataSourceModule {

    @ViewModelScoped
    @Provides
    fun provideCellTowersData(@ApplicationContext context: Context): CellTowersData {
        return CellTowersData(
            context
        )
    }

    @ViewModelScoped
    @Provides
    fun provideLocationData(@ApplicationContext context: Context): LocationData {
        return LocationData(
            context
        )
    }

    @ViewModelScoped
    @Provides
    fun provideGPSStatus(@ApplicationContext context: Context): GpsStatusData {
        return GpsStatusData(
            context
        )
    }

    @ViewModelScoped
    @Provides
    fun providePermissionsStatus(@ApplicationContext context: Context): PermissionsStatusData {
        return PermissionsStatusData(
            context
        )
    }

    @ViewModelScoped
    @Provides
    fun provideWifiScanResults(@ApplicationContext context: Context): WifiScanResultsData {
        return WifiScanResultsData(
            context
        )
    }

    @ViewModelScoped
    @Provides
    fun provideWifiStatus(@ApplicationContext context: Context): WifiStatusData {
        return WifiStatusData(
            context
        )
    }
}
