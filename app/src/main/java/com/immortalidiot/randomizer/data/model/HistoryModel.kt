package com.immortalidiot.randomizer.data.model

import com.immortalidiot.randomizer.data.ContentType
import java.time.LocalDateTime

data class HistoryModel(
    val id: Long,
    val time: LocalDateTime,
    val contentType: ContentType,
    val content: List<String>,
    val result: String
)
