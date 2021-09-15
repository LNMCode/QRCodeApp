package com.ricker.qrcodeapp.interactors.history

import com.ricker.qrcodeapp.cache.HistoryDao
import com.ricker.qrcodeapp.cache.model.HistoryEntityMapper

class UpdateHistory(
    private val historyDao: HistoryDao,
) {

    suspend fun updateFavorite(isFavorite: Boolean, idHistory: String){
        historyDao.updateFavorites(isFavorite, idHistory)
    }

}