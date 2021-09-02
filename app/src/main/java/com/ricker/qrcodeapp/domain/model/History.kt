package com.ricker.qrcodeapp.domain.model

data class History(
    val id: Int,
    val value: String,
    val scannedDay: String,
){
    constructor(value: String, scannedDay: String): this(0, value, scannedDay)
}