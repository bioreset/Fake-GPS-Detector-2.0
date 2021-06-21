package com.dariusz.fakegpsdetector.data.repository

import android.content.Context
import com.dariusz.fakegpsdetector.data.source.local.db.dao.LocationFromApiResponseDao
import com.dariusz.fakegpsdetector.data.source.remote.api.FakeGPSRestApiService
import com.dariusz.fakegpsdetector.model.ApiResponseModelJson
import com.dariusz.fakegpsdetector.model.CellTowerModel
import com.dariusz.fakegpsdetector.model.RoutersListModel
import com.dariusz.fakegpsdetector.utils.ManageResponse.asResponseToDb
import com.dariusz.fakegpsdetector.utils.ManageResponse.buildJSONRequest
import com.dariusz.fakegpsdetector.utils.ManageResponse.manageResponse
import com.dariusz.fakegpsdetector.utils.RepositoryUtils.performApiCall
import com.dariusz.fakegpsdetector.utils.RepositoryUtils.performCacheCall
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

@InternalCoroutinesApi
class LocationFromApiResponseRepository
@Inject
constructor(
    private val fakeGPSRestApiService: FakeGPSRestApiService,
    private val locationFromApiResponse: LocationFromApiResponseDao
) {

    private suspend fun insertAsFresh(apiResponse: ApiResponseModelJson) {
        performCacheCall(locationFromApiResponse.deleteAllLocationFromApiRecords())
        performCacheCall(locationFromApiResponse.insert(asResponseToDb(apiResponse)))
    }

    private suspend fun checkCurrentLocationOfTheDevice(body: String, context: Context): String =
        performApiCall(fakeGPSRestApiService.checkCurrentLocation(body, context))

    suspend fun manageResponseAfterAction(
        cellData: List<CellTowerModel>?,
        routersData: List<RoutersListModel>?,
        context: Context
    ) {
        val check =
            checkCurrentLocationOfTheDevice(buildJSONRequest(cellData, routersData), context)
        return manageResponse(check) { insertAsFresh(it) }
    }

    suspend fun checkLocationStatus() =
        performCacheCall(locationFromApiResponse.getLocationFromApiInfo())
}
