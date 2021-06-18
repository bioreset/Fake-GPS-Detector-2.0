package com.dariusz.fakegpsdetector.utils

import android.annotation.SuppressLint
import android.os.Build
import android.telephony.*
import androidx.annotation.RequiresApi
import com.dariusz.fakegpsdetector.model.CellTowerModel

object CellTowersUtils {

    @RequiresApi(Build.VERSION_CODES.P)
    @SuppressLint("ObsoleteSdkInt")
    fun mapCellTowers(model: List<CellInfo>?): List<CellTowerModel> =
        model?.mapNotNull {
            if (it is CellInfoGsm) {
                newCellTowersListGsm(it)
            } else if (it is CellInfoLte) {
                newCellTowersListLte(it)
            } else if (it is CellInfoCdma) {
                newCellTowersListCdma(it)
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2 && it is CellInfoWcdma) {
                newCellTowersListWcdma(it)
            } else null
        } ?: emptyList()

    @RequiresApi(Build.VERSION_CODES.P)
    private fun newCellTowersListLte(cltwm: CellInfoLte): CellTowerModel {
        return CellTowerModel(
            cellId = cltwm.cellIdentity.ci.toString(),
            locationAreaCode = cltwm.cellIdentity.tac,
            mobileCountryCode = cltwm.cellIdentity.mccString,
            mobileNetworkCode = cltwm.cellIdentity.mncString,
            signalStrength = cltwm.cellSignalStrength.dbm
        )
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun newCellTowersListGsm(cltwm: CellInfoGsm): CellTowerModel {
        return CellTowerModel(
            cellId = cltwm.cellIdentity.cid.toString(),
            locationAreaCode = cltwm.cellIdentity.lac,
            mobileCountryCode = cltwm.cellIdentity.mccString,
            mobileNetworkCode = cltwm.cellIdentity.mncString,
            signalStrength = cltwm.cellSignalStrength.dbm
        )
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun newCellTowersListWcdma(cltwm: CellInfoWcdma): CellTowerModel {
        return CellTowerModel(
            cellId = cltwm.cellIdentity.cid.toString(),
            locationAreaCode = cltwm.cellIdentity.lac,
            mobileCountryCode = cltwm.cellIdentity.mccString,
            mobileNetworkCode = cltwm.cellIdentity.mncString,
            signalStrength = cltwm.cellSignalStrength.dbm
        )
    }

    private fun newCellTowersListCdma(cltwm: CellInfoCdma): CellTowerModel {
        return CellTowerModel(
            cellId = cltwm.cellIdentity.basestationId.toString(),
            locationAreaCode = cltwm.cellIdentity.latitude + cltwm.cellIdentity.longitude,
            mobileCountryCode = cltwm.cellIdentity.systemId.toString(),
            mobileNetworkCode = cltwm.cellIdentity.networkId.toString(),
            signalStrength = cltwm.cellSignalStrength.dbm
        )
    }
}
