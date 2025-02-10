package com.immortalidiot.randomizer.data.history

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.immortalidiot.randomizer.data.Converters

@Database(
    version = 1,
    entities =[History::class]
)
@TypeConverters(Converters::class)
abstract class HistoryDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryRepository

    companion object {
        const val DATABASE_NAME = "History"
    }
}
