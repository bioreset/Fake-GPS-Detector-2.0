package com.dariusz.fakegpsdetector.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiResponseModel(
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


