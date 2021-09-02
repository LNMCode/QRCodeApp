package com.ricker.qrcodeapp.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_table")
class HistoryEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "value")
    var value: String,

    @ColumnInfo(name = "scanned_day")
    var scannedDay: String,

)