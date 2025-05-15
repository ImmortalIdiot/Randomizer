package com.immortalidiot.randomizer.data.history

import androidx.compose.runtime.Immutable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.immortalidiot.randomizer.data.Content
import com.immortalidiot.randomizer.data.ContentType
import java.time.LocalDateTime

@Entity
@Immutable
data class History(
    val time: LocalDateTime,
    val contentType: ContentType,
    val content: Content,
    val result: String,
    @PrimaryKey(autoGenerate = true) val id: Long = 0
)
