package com.dariusz.fakegpsdetector.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiRequestModel(
    @field:Json(name = "wifiAccessPoints")
    val routersList: List<RoutersListModel>?,
    @field:Json(name = "cellTowers")
    val cellTowersList: List<CellTowerModel>?

)
