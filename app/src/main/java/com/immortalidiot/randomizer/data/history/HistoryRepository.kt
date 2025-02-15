package com.immortalidiot.randomizer.data.history

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface HistoryRepository {

    @Upsert
    suspend fun saveHistory(history: History)

    @Query("SELECT * FROM HISTORY")
    suspend fun getAllHistory(): List<History>

    @Query("DELETE FROM History")
    suspend fun deleteAllHistory()

    @Query("DELETE FROM History WHERE History.id = :id")
    suspend fun deleteHistoryById(id: Long)
}