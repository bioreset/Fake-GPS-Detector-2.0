package com.dariusz.fakegpsdetector.di

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.dariusz.fakegpsdetector.data.local.sensor.celltowers.CellTowersData
import com.dariusz.fakegpsdetector.data.local.sensor.celltowers.CellTowersDataAPI29Plus
import com.dariusz.fakegpsdetector.data.local.sensor.celltowers.CellTowersDataAPI29PlusImpl
import com.dariusz.fakegpsdetector.data.local.sensor.celltowers.CellTowersDataImpl
import com.dariusz.fakegpsdetector.data.local.sensor.location.LocationData
import com.dariusz.fakegpsdetector.data.local.sensor.location.LocationDataImpl
import com.dariusz.fakegpsdetector.data.local.sensor.requirements.*
import com.dariusz.fakegpsdetector.data.local.sensor.wifinodes.WifiScanResultsData
import com.dariusz.fakegpsdetector.data.local.sensor.wifinodes.WifiScanResultsDataImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    fun provideCellTowersData(@ApplicationContext context: Context): CellTowersData {
        return CellTowersDataImpl(
            context
        )
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    @Provides
    fun provideCellTowersDataApi29Plus(@ApplicationContext context: Context): CellTowersDataAPI29Plus {
        return CellTowersDataAPI29PlusImpl(
            context
        )
    }

    @Provides
    fun provideLocationData(@ApplicationContext context: Context): LocationData {
        return LocationDataImpl(
            context
        )
    }

    @Provides
    fun provideGPSStatus(@ApplicationContext context: Context): GpsStatusData {
        return GpsStatusDataImpl(
            context
        )
    }

    @Provides
    fun providePermissionsStatus(@ApplicationContext context: Context): PermissionsStatusData {
        return PermissionsStatusDataImpl(
            context
        )
    }

    @Provides
    fun provideWifiScanResults(@ApplicationContext context: Context): WifiScanResultsData {
        return WifiScanResultsDataImpl(
            context
        )
    }

    @Provides
    fun provideWifiStatus(@ApplicationContext context: Context): WifiStatusData {
        return WifiStatusDataImpl(
            context
        )
    }
}
