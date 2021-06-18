package com.dariusz.fakegpsdetector.data.source.local.db.init

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dariusz.fakegpsdetector.data.source.local.db.dao.CellTowersDao
import com.dariusz.fakegpsdetector.data.source.local.db.dao.LocationDao
import com.dariusz.fakegpsdetector.data.source.local.db.dao.LocationFromApiResponseDao
import com.dariusz.fakegpsdetector.data.source.local.db.dao.RoutersListDao
import com.dariusz.fakegpsdetector.model.ApiResponseModel
import com.dariusz.fakegpsdetector.model.CellTowerModel
import com.dariusz.fakegpsdetector.model.LocationModel
import com.dariusz.fakegpsdetector.model.RoutersListModel
import com.dariusz.fakegpsdetector.utils.Constants.DB_VER
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@Database(
    entities = [CellTowerModel::class, LocationModel::class, RoutersListModel::class, ApiResponseModel::class],
    version = DB_VER,
    exportSchema = false
)

abstract class FGDDatabase : RoomDatabase() {

    abstract fun locationDao(): LocationDao
    abstract fun routersListDao(): RoutersListDao
    abstract fun cellTowersDao(): CellTowersDao
    abstract fun locationFromApiResponseDao(): LocationFromApiResponseDao
}
