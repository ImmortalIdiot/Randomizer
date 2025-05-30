package com.immortalidiot.randomizer.data.history

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryRepository {

    @Insert
    suspend fun saveHistory(history: History)

    @Query("SELECT * FROM HISTORY ORDER BY time DESC")
    fun getAllHistory(): Flow<List<History>>

    @Query("DELETE FROM History")
    suspend fun deleteAllHistory()

    @Query("DELETE FROM History WHERE History.id = :id")
    suspend fun deleteHistoryById(id: Long)

    @Delete
    suspend fun deleteByList(items: List<History>)
}
