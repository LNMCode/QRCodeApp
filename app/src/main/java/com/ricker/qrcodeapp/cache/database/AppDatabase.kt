package com.ricker.qrcodeapp.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ricker.qrcodeapp.cache.HistoryDao
import com.ricker.qrcodeapp.cache.model.HistoryEntity

@Database(entities = [HistoryEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun historyDao(): HistoryDao

    companion object{
        val DATABASE_NAME: String = "history_db"
    }
}