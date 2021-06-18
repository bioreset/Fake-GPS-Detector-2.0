package com.dariusz.fakegpsdetector.data.source.local.db.dao

import androidx.room.*
import com.dariusz.fakegpsdetector.model.CellTowerModel

@Dao
interface CellTowersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cellTowerList: List<CellTowerModel>)

    @Query("DELETE FROM celltowers_table")
    suspend fun deleteAllCellTowers()

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT id, cellId, locationAreaCode, mobileCountryCode, mobileNetworkCode, signalStrength FROM celltowers_table;")
    suspend fun getAllCellTowers(): List<CellTowerModel>
}
