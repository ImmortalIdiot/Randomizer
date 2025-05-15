package com.immortalidiot.randomizer

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.immortalidiot.randomizer.data.Content
import com.immortalidiot.randomizer.data.ContentType
import com.immortalidiot.randomizer.data.history.History
import com.immortalidiot.randomizer.data.history.HistoryDatabase
import com.immortalidiot.randomizer.data.history.HistoryRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime

class HistoryDatabaseTest {

    private lateinit var database: HistoryDatabase
    private lateinit var historyDao: HistoryRepository

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            HistoryDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
        historyDao = database.historyDao()
    }

    @After
    fun tearDown() {
        if (::database.isInitialized) {
            database.close()
        }
    }

    @Test
    fun testRangeResultToHistory() = runBlocking {
        val history = History(
            time = LocalDateTime.now(),
            contentType = ContentType.RANGE,
            content = Content.Range(first = 1, second = 10),
            result = "5"
        )

        historyDao.saveHistory(history)

        val allHistory = historyDao.getAllHistory().first()
        assertEquals(1, allHistory.size)
        val saved = allHistory.first()

        assertEquals(history.result, saved.result)
        assertEquals(history.contentType, saved.contentType)
        assertEquals(history.content, saved.content)

        @Suppress("USELESS_IS_CHECK")
        assert(saved.time is LocalDateTime)
    }
}
