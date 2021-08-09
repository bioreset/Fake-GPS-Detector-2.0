package com.dariusz.fakegpsdetector.domain.repository

import android.telephony.CellInfo
import com.dariusz.fakegpsdetector.data.local.sensor.celltowers.CellTowersData
import com.dariusz.fakegpsdetector.data.local.sensor.celltowers.CellTowersDataAPI29Plus
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface CellTowersDataRepository {

    suspend fun getCellTowers(newApi: Boolean): Flow<List<CellInfo>>

}

class CellTowersDataRepositoryImpl
@Inject constructor(
    private val cellTowersData: CellTowersData,
    private val cellTowersDataAPI29Plus: CellTowersDataAPI29Plus
) : CellTowersDataRepository {

    override suspend fun getCellTowers(newApi: Boolean) =
        if (!newApi)
            cellTowersData.getCurrentCellTowersLive()
        else
            cellTowersDataAPI29Plus.getCurrentCellTowersLive()

}