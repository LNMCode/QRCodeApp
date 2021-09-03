package com.ricker.qrcodeapp.domain.model

data class History(
    val id: String,
    val value: String,
    val isFavorite: Boolean,
    val scannedDay: String,
)