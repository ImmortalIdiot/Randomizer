package com.immortalidiot.randomizer.data.di

import android.content.Context
import androidx.room.Room
import com.immortalidiot.randomizer.data.history.HistoryDatabase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


private fun provideHistoryDatabase(context: Context): HistoryDatabase {
    return Room.databaseBuilder(
        context = context,
        klass = HistoryDatabase::class.java,
        name = HistoryDatabase.DATABASE_NAME
    ).build()
}

val databaseModule = module {
    singleOf(::provideHistoryDatabase)
    single { get<HistoryDatabase>().historyDao() }
}
