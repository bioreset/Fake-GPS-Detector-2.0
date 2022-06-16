package com.dariusz.fakegpsdetector.domain.model

import android.net.wifi.ScanResult
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RoutersListModel(
    @field:Json(name = "ssid")
    val ssid: String?,
    @field:Json(name = "macAddress")
    val macAddress: String,
    @field:Json(name = "frequency")
    val frequency: Int,
    @field:Json(name = "level")
    val level: Int,
    val channel: Int? = 0,
    val qualityOfConnection: String? = ""
) {
    companion object {
        fun newRoutersList(rmdl: List<ScanResult>?): List<RoutersListModel> {
            return rmdl?.map {
                RoutersListModel(
                    ssid = it.SSID + " (" + (when (it.frequency) {
                        in 2401..2495 -> "2,4"
                        in 5030..5895 -> "5"
                        else -> "N/A"
                    }) + " Ghz)",
                    macAddress = it.BSSID,
                    frequency = it.frequency,
                    level = it.level,
                    channel = (when (it.frequency) {
                        2412 -> 1
                        2417 -> 2
                        2422 -> 3
                        2427 -> 4
                        2432 -> 5
                        2437 -> 6
                        2442 -> 7
                        2447 -> 8
                        2452 -> 9
                        2457 -> 10
                        2462 -> 11
                        2467 -> 12
                        2472 -> 13
                        2484 -> 14

                        5160 -> 32
                        5170 -> 34
                        5180 -> 36
                        5190 -> 38
                        5200 -> 40
                        5210 -> 42
                        5220 -> 44
                        5230 -> 46
                        5240 -> 48
                        5250 -> 50
                        5260 -> 52
                        5270 -> 54
                        5280 -> 56
                        5290 -> 58
                        5300 -> 60
                        5310 -> 62
                        5320 -> 64
                        5340 -> 66
                        else -> 0
                    }),
                    qualityOfConnection = (when (it.level) {
                        in -30 downTo -66 -> "Amazing"
                        in -67 downTo -70 -> "Great"
                        in -71 downTo -80 -> "Good"
                        in -81 downTo -90 -> "Bad"
                        in -91 downTo -100 -> "Unusable"
                        else -> "N/A"
                    })
                )
            }
                ?: listOf()
        }
    }
}
