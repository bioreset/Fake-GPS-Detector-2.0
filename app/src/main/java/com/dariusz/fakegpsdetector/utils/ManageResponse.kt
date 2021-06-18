package com.dariusz.fakegpsdetector.utils

import com.dariusz.fakegpsdetector.model.*
import com.dariusz.fakegpsdetector.utils.RepositoryUtils.performCacheCall
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
object ManageResponse {

    private val moshi: Moshi = Moshi.Builder().build()

    suspend fun manageResponse(
        response: String,
        cacheCall: suspend (ApiResponseModelJson) -> Unit?
    ) =
        performCacheCall(
            response.let {
                parseResponse(it)?.let { it2 ->
                    cacheCall.invoke(
                        it2
                    )
                }
            }
        )

    fun buildJSONRequest(
        cellData: List<CellTowerModel>?,
        routersData: List<RoutersListModel>?
    ): String {
        val apiRequest = ApiRequestModel(routersData, cellData)
        val adapter: JsonAdapter<ApiRequestModel> = moshi.adapter(ApiRequestModel::class.java)
        return adapter.toJson(apiRequest)
    }

    private fun parseResponse(response: String): ApiResponseModelJson? {
        val adapter: JsonAdapter<ApiResponseModelJson> =
            moshi.adapter(ApiResponseModelJson::class.java)
        return adapter.fromJson(response)
    }

    fun asResponseToDb(response: ApiResponseModelJson): ApiResponseModel {
        return ApiResponseModel(
            status = "location",
            lat = response.location.lat,
            lng = response.location.lng,
            accuracy = response.accuracy
        )
    }
}
