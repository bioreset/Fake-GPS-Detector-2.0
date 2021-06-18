package com.dariusz.fakegpsdetector.model

import android.net.wifi.ScanResult
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "routers_table")
data class RoutersListModel(
    @ColumnInfo(name = "ssid")
    @field:Json(name = "ssid")
    val ssid: String?,
    @ColumnInfo(name = "macAddress")
    @field:Json(name = "macAddress")
    val macAddress: String,
    @ColumnInfo(name = "frequency")
    @field:Json(name = "frequency")
    val frequency: Int,
    @ColumnInfo(name = "level")
    @field:Json(name = "level")
    val level: Int
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0

    companion object {
        fun newRoutersList(rmdl: List<ScanResult>): List<RoutersListModel> {
            return rmdl.map {
                RoutersListModel(
                    ssid = it.SSID,
                    macAddress = it.BSSID,
                    frequency = it.frequency,
                    level = it.level
                )
            }
        }
    }
}
