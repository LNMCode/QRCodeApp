package com.ricker.qrcodeapp.domain.util

interface DomainMapper <T, DomainModel> {
    fun mapToDomainModel(model: T): DomainModel

    fun mapFromDomainModel(model: DomainModel): T

    fun toDomainList(initial: List<T>): List<DomainModel>

    fun fromDomainList(initial: List<DomainModel>): List<T>
}