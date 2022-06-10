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
import com.dariusz.fakegpsdetector.data.remote.api.FakeGPSRestApi
import com.dariusz.fakegpsdetector.data.remote.api.FakeGPSRestApiServiceImpl
import com.dariusz.fakegpsdetector.domain.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideLocationFromApiResponseRepository(retrofit: FakeGPSRestApi): LocationFromApiResponseRepository =
        LocationFromApiResponseRepository(
            FakeGPSRestApiServiceImpl(retrofit)
        )

    @SuppressLint("NewApi")
    @Provides
    fun provideCellTowersDataRepository(@ApplicationContext context: Context): CellTowersDataRepository =
        CellTowersDataRepository(
            CellTowersDataImpl(
                context
            ),
            CellTowersDataAPI29PlusImpl(
                context
            )
        )

    @Provides
    fun provideLocationRepository(@ApplicationContext context: Context): LocationRepository =
        LocationRepository(
            LocationDataImpl(
                context
            )
        )

    @Provides
    fun provideRequirementsRepository(@ApplicationContext context: Context): RequirementsRepository =
        RequirementsRepository(
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
        WifiNodesRepository(
            WifiScanResultsDataImpl(
                context
            )
        )


}
