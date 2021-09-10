package com.ricker.qrcodeapp.cache

import androidx.room.*
import com.ricker.qrcodeapp.cache.model.HistoryEntity

@Dao
interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(historyEntity: HistoryEntity): Long

    @Query("SELECT * FROM history_table WHERE id = :id")
    suspend fun getHistoryItemById(id: String): HistoryEntity?

    @Query("DELETE FROM history_table WHERE id IN (:ids)")
    suspend fun deleteMoreHistoryItemById(ids: List<Int>): Int

    @Query("DELETE FROM history_table")
    suspend fun deleteAllHistoryItem()

    @Query("DELETE FROM history_table WHERE id = :primaryKey")
    suspend fun deleteHistoryItem(primaryKey: Int): Int

    @Query("SELECT * FROM history_table")
    suspend fun getAllHistoryItem(): List<HistoryEntity>

    @Query("SELECT * FROM history_table WHERE is_favorite = :isFavorite")
    suspend fun getFavorites(isFavorite: Boolean): List<HistoryEntity>

}