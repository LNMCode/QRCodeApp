package com.ricker.qrcodeapp.interactors.history

import com.ricker.qrcodeapp.cache.HistoryDao
import com.ricker.qrcodeapp.cache.model.HistoryEntityMapper
import com.ricker.qrcodeapp.domain.model.History

class GetFavorite(
    private val historyDao: HistoryDao,
    private val entityMapper: HistoryEntityMapper,
) {

    suspend fun getAll(isFavorite: Boolean): List<History>{
        val cacheResult = historyDao.getFavorites(isFavorite = isFavorite)
        return entityMapper.toDomainList(cacheResult)
    }

}