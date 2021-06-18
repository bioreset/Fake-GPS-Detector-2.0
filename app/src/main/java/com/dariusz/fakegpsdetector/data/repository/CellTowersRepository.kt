package com.dariusz.fakegpsdetector.data.repository

import com.dariusz.fakegpsdetector.data.source.local.db.dao.CellTowersDao
import com.dariusz.fakegpsdetector.model.CellTowerModel
import com.dariusz.fakegpsdetector.utils.RepositoryUtils.performCacheCall
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

@InternalCoroutinesApi
class CellTowersRepository
@Inject
constructor(
    private val cellTowersDao: CellTowersDao
) {
    suspend fun insertAsFresh(cellTowerList: List<CellTowerModel>) {
        performCacheCall(cellTowersDao.deleteAllCellTowers())
        performCacheCall(cellTowersDao.insertAll(cellTowerList))
    }

    suspend fun selectAll() =
        performCacheCall(cellTowersDao.getAllCellTowers())
}
