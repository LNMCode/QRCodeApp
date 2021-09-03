package com.ricker.qrcodeapp.cache.model

import com.ricker.qrcodeapp.domain.model.History
import com.ricker.qrcodeapp.domain.util.DomainMapper

class HistoryEntityMapper : DomainMapper<HistoryEntity, History> {

    override fun mapToDomainModel(model: HistoryEntity): History {
        return History(
            id = model.id,
            value = model.value,
            isFavorite = model.isFavorite,
            scannedDay = model.scannedDay,
        )
    }

    override fun mapFromDomainModel(model: History): HistoryEntity {
        return HistoryEntity(
            id = model.id,
            value = model.value,
            isFavorite = model.isFavorite,
            scannedDay = model.scannedDay,
        )
    }

    override fun toDomainList(initial: List<HistoryEntity>): List<History> {
        return initial.map { mapToDomainModel(it) }
    }

    override fun fromDomainList(initial: List<History>): List<HistoryEntity> {
        return initial.map { mapFromDomainModel(it) }
    }

}