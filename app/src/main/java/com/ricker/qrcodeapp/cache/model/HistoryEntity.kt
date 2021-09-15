package com.ricker.qrcodeapp.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_table")
class HistoryEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: String,

    @ColumnInfo(name = "value")
    var value: String,

    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean,

    @ColumnInfo(name = "scanned_day")
    var scannedDay: String,

)