package com.immortalidiot.randomizer.data.di

import android.content.Context
import androidx.room.Room
import com.immortalidiot.randomizer.data.history.HistoryDatabase
import com.immortalidiot.randomizer.data.history.Migrations
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


private fun provideHistoryDatabase(context: Context): HistoryDatabase {
    return Room.databaseBuilder(
        context = context,
        klass = HistoryDatabase::class.java,
        name = HistoryDatabase.DATABASE_NAME
    )
        .addMigrations(Migrations.MIGRATION_1_2)
        .build()
}

val databaseModule = module {
    singleOf(::provideHistoryDatabase)
    single { get<HistoryDatabase>().historyDao() }
}
