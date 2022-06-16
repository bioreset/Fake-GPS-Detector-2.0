package com.dariusz.fakegpsdetector.domain.repository

import com.dariusz.fakegpsdetector.data.local.sensor.celltowers.CellTowersData
import com.dariusz.fakegpsdetector.data.local.sensor.celltowers.CellTowersDataAPI29Plus
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CellTowersDataRepository
@Inject constructor(
    private val cellTowersData: CellTowersData,
    private val cellTowersDataAPI29Plus: CellTowersDataAPI29Plus
) {

    fun getCellTowers(newApi: Boolean) =
        if (!newApi)
            cellTowersData.getCurrentCellTowers()
        else
            cellTowersDataAPI29Plus.getCurrentCellTowersLive()

}