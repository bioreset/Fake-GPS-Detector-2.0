package com.dariusz.fakegpsdetector.di

import android.annotation.SuppressLint
import android.content.Context
import com.dariusz.fakegpsdetector.data.local.sensor.celltowers.CellTowersDataAPI29PlusImpl
import com.dariusz.fakegpsdetector.data.local.sensor.celltowers.CellTowersDataImpl
import com.dariusz.fakegpsdetector.data.local.sensor.location.LocationDataImpl
import com.dariusz.fakegpsdetector.data.local.sensor.requirements.GpsStatusDataImpl
import com.dariusz.fakegpsdetector.data.local.sensor.requirements.PermissionsStatusDataImpl
import com.dariusz.fakegpsdetector.data.local.sensor.requirements.WifiStatusDataImpl
import com.dariusz.fakegpsdetector.data.local.sensor.wifinodes.WifiScanResultsDataImpl
import com.dariusz.fakegpsdetector.data.remote.api.FakeGPSRestApiServiceImpl
import com.dariusz.fakegpsdetector.domain.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@Module
@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@InstallIn(SingletonComponent::class)
object RepositoryModule {


    @Provides
    fun provideLocationFromApiResponseRepository(): LocationFromApiResponseRepository =
        LocationFromApiResponseRepositoryImpl(
            FakeGPSRestApiServiceImpl()
        )

    @SuppressLint("NewApi")
    @Provides
    fun provideCellTowersDataRepository(@ApplicationContext context: Context): CellTowersDataRepository =
        CellTowersDataRepositoryImpl(
            CellTowersDataImpl(
                context
            ),
            CellTowersDataAPI29PlusImpl(
                context
            )
        )

    @Provides
    fun provideLocationRepository(@ApplicationContext context: Context): LocationRepository =
        LocationRepositoryImpl(
            LocationDataImpl(
                context
            )
        )

    @Provides
    fun provideRequirementsRepository(@ApplicationContext context: Context): RequirementsRepository =
        RequirementsRepositoryImpl(
            GpsStatusDataImpl(
                context
            ),
            PermissionsStatusDataImpl(
                context
            ),
            WifiStatusDataImpl(
                context
            )
        )

    @Provides
    fun provideWifiNodesRepository(@ApplicationContext context: Context): WifiNodesRepository =
        WifiNodesRepositoryImpl(
            WifiScanResultsDataImpl(
                context
            )
        )


}