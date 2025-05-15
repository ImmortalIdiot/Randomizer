package com.immortalidiot.randomizer.data.history

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.immortalidiot.randomizer.data.Converters

@Database(
    version = 2,
    entities =[History::class],
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class HistoryDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryRepository

    companion object {
        const val DATABASE_NAME = "History"
    }
}

object Migrations {
    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {}
    }
}
