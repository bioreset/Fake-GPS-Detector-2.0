package com.dariusz.fakegpsdetector.di

import android.content.Context
import androidx.room.Room
import com.dariusz.fakegpsdetector.data.source.local.db.dao.CellTowersDao
import com.dariusz.fakegpsdetector.data.source.local.db.dao.LocationDao
import com.dariusz.fakegpsdetector.data.source.local.db.dao.LocationFromApiResponseDao
import com.dariusz.fakegpsdetector.data.source.local.db.dao.RoutersListDao
import com.dariusz.fakegpsdetector.data.source.local.db.init.FGDDatabase
import com.dariusz.fakegpsdetector.utils.Constants.DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@Module
@InstallIn(ViewComponent::class)
object CacheModule {

    @Provides
    fun buildDatabase(@ApplicationContext context: Context): FGDDatabase {
        return Room.databaseBuilder(context, FGDDatabase::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideCellTowersDAO(@ApplicationContext context: Context): CellTowersDao {
        return buildDatabase(context).cellTowersDao()
    }

    @Provides
    fun provideLocationDAO(@ApplicationContext context: Context): LocationDao {
        return buildDatabase(context).locationDao()
    }

    @Provides
    fun provideLocationFromApiResponseDAO(@ApplicationContext context: Context): LocationFromApiResponseDao {
        return buildDatabase(context).locationFromApiResponseDao()
    }

    @Provides
    fun provideRoutersListDAO(@ApplicationContext context: Context): RoutersListDao {
        return buildDatabase(context).routersListDao()
    }
}
