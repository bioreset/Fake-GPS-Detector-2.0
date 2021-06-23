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
        performCacheCall("delete-all-cell-towers-in-db", cellTowersDao.deleteAllCellTowers())
        performCacheCall("insert-all-cell-towers-to-db", cellTowersDao.insertAll(cellTowerList))
    }

    suspend fun selectAll() =
        performCacheCall("select-all-cell-towers-from-db", cellTowersDao.getAllCellTowers())
}
