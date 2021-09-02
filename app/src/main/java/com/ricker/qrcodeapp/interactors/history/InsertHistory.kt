package com.ricker.qrcodeapp.interactors.history

import com.ricker.qrcodeapp.cache.HistoryDao
import com.ricker.qrcodeapp.cache.model.HistoryEntityMapper
import com.ricker.qrcodeapp.domain.data.DataState
import com.ricker.qrcodeapp.domain.model.History
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class InsertHistory(
    private val historyDao: HistoryDao,
    private val entityMapper: HistoryEntityMapper,
) {

    suspend fun insert(
        model: History
    ){
        historyDao.insertHistory(entityMapper.mapFromDomainModel(model))
    }

}