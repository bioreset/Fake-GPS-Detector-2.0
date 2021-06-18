package com.dariusz.fakegpsdetector.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "celltowers_table")
data class CellTowerModel(
    @ColumnInfo(name = "cellId")
    @field:Json(name = "cellId")
    val cellId: String,
    @ColumnInfo(name = "locationAreaCode")
    @field:Json(name = "locationAreaCode")
    val locationAreaCode: Int,
    @ColumnInfo(name = "mobileCountryCode")
    @field:Json(name = "mobileCountryCode")
    val mobileCountryCode: String? = null,
    @ColumnInfo(name = "mobileNetworkCode")
    @field:Json(name = "mobileNetworkCode")
    val mobileNetworkCode: String? = null,
    @ColumnInfo(name = "signalStrength")
    val signalStrength: Int
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
}
