package com.dariusz.fakegpsdetector.data.source.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dariusz.fakegpsdetector.model.ApiResponseModel

@Dao
interface LocationFromApiResponseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(apiResponse: ApiResponseModel)

    @Query("DELETE FROM locationfromapix_table")
    suspend fun deleteAllLocationFromApiRecords()

    @Query("SELECT * FROM locationfromapix_table;")
    suspend fun getLocationFromApiInfo(): ApiResponseModel
}
