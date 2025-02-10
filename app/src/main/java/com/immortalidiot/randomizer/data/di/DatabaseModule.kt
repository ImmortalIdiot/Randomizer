package com.immortalidiot.randomizer.data.di

import android.content.Context
import androidx.room.Room
import com.immortalidiot.randomizer.data.history.HistoryDatabase
import com.immortalidiot.randomizer.data.history.HistoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideHistoryDatabase(@ApplicationContext context: Context): HistoryDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = HistoryDatabase::class.java,
            name = HistoryDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideHistoryRepository(db: HistoryDatabase): HistoryRepository {
        return db.historyDao()
    }
}
