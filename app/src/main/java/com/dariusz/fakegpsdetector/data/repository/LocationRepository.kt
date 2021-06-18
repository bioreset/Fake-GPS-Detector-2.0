package com.dariusz.fakegpsdetector.data.repository

import com.dariusz.fakegpsdetector.data.source.local.db.dao.LocationDao
import com.dariusz.fakegpsdetector.model.LocationModel
import com.dariusz.fakegpsdetector.utils.RepositoryUtils.performCacheCall
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

@InternalCoroutinesApi
class LocationRepository
@Inject
constructor(
    private val locationDao: LocationDao
) {

    suspend fun insertAsFresh(location: LocationModel) {
        performCacheCall(locationDao.deleteAllLocationInfo())
        performCacheCall(locationDao.insert(location))
    }

    suspend fun selectAll() = performCacheCall(locationDao.getLocation())
}
