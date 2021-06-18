package com.dariusz.fakegpsdetector.data.source.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dariusz.fakegpsdetector.model.LocationModel

@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(locationModel: LocationModel)

    @Query("DELETE FROM location")
    suspend fun deleteAllLocationInfo()

    @Query("SELECT * FROM location;")
    suspend fun getLocation(): LocationModel
}
