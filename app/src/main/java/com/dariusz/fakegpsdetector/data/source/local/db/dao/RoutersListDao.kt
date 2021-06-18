package com.dariusz.fakegpsdetector.data.source.local.db.dao

import androidx.room.*
import com.dariusz.fakegpsdetector.model.RoutersListModel

@Dao
interface RoutersListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cellTowerList: List<RoutersListModel>)

    @Query("DELETE FROM routers_table")
    suspend fun deleteAllRouters()

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT id, ssid, macAddress, frequency, level FROM routers_table;")
    suspend fun getAllRouters(): List<RoutersListModel>
}
