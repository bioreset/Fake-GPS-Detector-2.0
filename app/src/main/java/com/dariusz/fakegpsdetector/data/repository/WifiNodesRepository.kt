package com.dariusz.fakegpsdetector.data.repository

import com.dariusz.fakegpsdetector.data.source.local.db.dao.RoutersListDao
import com.dariusz.fakegpsdetector.model.RoutersListModel
import com.dariusz.fakegpsdetector.utils.RepositoryUtils.performCacheCall
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

@InternalCoroutinesApi
class WifiNodesRepository
@Inject
constructor(
    private val routersListDao: RoutersListDao
) {

    suspend fun insertAsFresh(routerList: List<RoutersListModel>) {
        performCacheCall("delete-all-routers-from-db", routersListDao.deleteAllRouters())
        performCacheCall("insert-all-routers-to-db", routersListDao.insertAll(routerList))
    }

    suspend fun selectAll() =
        performCacheCall("select-all-routers-from-db", routersListDao.getAllRouters())
}
