package com.immortalidiot.randomizer.data.model

import com.immortalidiot.randomizer.core.DEFAULT_HISTORY_ID
import com.immortalidiot.randomizer.data.ContentType
import java.time.LocalDateTime

object HistoryModelFabric {
    fun createHistory(
        contentType: ContentType,
        content: List<String>,
        result: String
    ): HistoryModel {
        return HistoryModel(
            id = DEFAULT_HISTORY_ID,
            time = LocalDateTime.now(),
            contentType = contentType,
            content = content,
            result = result
        )
    }
}

data class HistoryModel(
    val id: Long,
    val time: LocalDateTime,
    val contentType: ContentType,
    val content: List<String>,
    val result: String
)
