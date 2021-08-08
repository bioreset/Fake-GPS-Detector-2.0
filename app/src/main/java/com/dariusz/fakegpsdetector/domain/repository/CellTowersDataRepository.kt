package com.dariusz.fakegpsdetector.domain.repository

import android.os.Build
import android.telephony.CellInfo
import com.dariusz.fakegpsdetector.data.local.sensor.celltowers.CellTowersData
import com.dariusz.fakegpsdetector.data.local.sensor.celltowers.CellTowersDataAPI29Plus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface CellTowersDataRepository {

    suspend fun getCellTowers(): Flow<List<CellInfo>>

}


class CellTowersDataRepositoryImpl
@Inject constructor(
    private val cellTowersData: CellTowersData,
    private val cellTowersDataAPI29Plus: CellTowersDataAPI29Plus
) : CellTowersDataRepository {

    override suspend fun getCellTowers() =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            cellTowersData.getCurrentCellTowersLive().map {
                return@map it
            }
        else
            cellTowersDataAPI29Plus.getCurrentCellTowersLive().map {
                return@map it
            }

}