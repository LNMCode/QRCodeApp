package com.ricker.qrcodeapp.interactors.history

import com.ricker.qrcodeapp.cache.HistoryDao
import com.ricker.qrcodeapp.cache.model.HistoryEntityMapper

class DeleteHistory(
    private val historyDao: HistoryDao,
) {

    suspend fun removeItemsById(ids: List<String>){
        historyDao.deleteHistoryItemsById(ids)
    }

}