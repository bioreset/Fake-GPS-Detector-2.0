package com.dariusz.fakegpsdetector.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CellTowerModel(
    @field:Json(name = "cellId")
    val cellId: String,
    @field:Json(name = "locationAreaCode")
    val locationAreaCode: Int,
    @field:Json(name = "mobileCountryCode")
    val mobileCountryCode: String? = null,
    @field:Json(name = "mobileNetworkCode")
    val mobileNetworkCode: String? = null,
    val signalStrength: Int
)