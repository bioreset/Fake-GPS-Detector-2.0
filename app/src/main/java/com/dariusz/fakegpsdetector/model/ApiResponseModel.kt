package com.dariusz.fakegpsdetector.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity(tableName = "locationfromapix_table")
data class ApiResponseModel(
    @ColumnInfo(name = "status")
    var status: String? = "",
    @ColumnInfo(name = "lat")
    var lat: Double? = 0.0,
    @ColumnInfo(name = "lng")
    var lng: Double? = 0.0,
    @ColumnInfo(name = "accuracy")
    var accuracy: Int? = 0
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
}

@JsonClass(generateAdapter = true)
data class ApiResponseModelJson(
    @field:Json(name = "location")
    var location: LocationData,
    @field:Json(name = "accuracy")
    var accuracy: Int? = 0
)

@JsonClass(generateAdapter = true)
data class LocationData(
    @field:Json(name = "lat")
    var lat: Double? = 0.0,
    @field:Json(name = "lng")
    var lng: Double? = 0.0
)
