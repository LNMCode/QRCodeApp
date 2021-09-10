package com.ricker.qrcodeapp.interactors.history

import com.ricker.qrcodeapp.cache.HistoryDao
import com.ricker.qrcodeapp.cache.model.HistoryEntityMapper
import com.ricker.qrcodeapp.domain.model.History

class GetHistory(
    private val historyDao: HistoryDao,
    private val entityMapper: HistoryEntityMapper,
) {

    suspend fun getAll(): List<History> {
        val cacheResult = historyDao.getAllHistoryItem()
        return entityMapper.toDomainList(cacheResult)
    }

    suspend fun getById(id: String): History? {
        val cacheResult = historyDao.getHistoryItemById(id)
        if (cacheResult != null)
            return entityMapper.mapToDomainModel(cacheResult)
        return null
    }

}