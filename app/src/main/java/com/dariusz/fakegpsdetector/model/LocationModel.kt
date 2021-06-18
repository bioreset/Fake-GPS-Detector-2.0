package com.dariusz.fakegpsdetector.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location")
data class LocationModel(
    @ColumnInfo(name = "longitude") val longitude: Double,
    @ColumnInfo(name = "latitude") val latitude: Double
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
}
