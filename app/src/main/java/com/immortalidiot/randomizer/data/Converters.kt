package com.immortalidiot.randomizer.data

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

object Converters {

    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    fun localDateTimeToLong(localDateTime: LocalDateTime): Long =
        localDateTime.atZone(ZoneId.systemDefault()).toInstant().epochSecond

    @TypeConverter
    fun longToLocalDateTime(timestamp: Long): LocalDateTime =
        Instant.ofEpochSecond(timestamp).atZone(ZoneId.systemDefault()).toLocalDateTime()

    @TypeConverter
    fun fromContent(content: Content): String {
        return json.encodeToString(content)
    }

    @TypeConverter
    fun toContent(jsonString: String): Content {
        return json.decodeFromString(jsonString)
    }

    @TypeConverter
    fun fromContentType(contentType: ContentType): String = contentType.name

    @TypeConverter
    fun toContentType(value: String): ContentType = ContentType.valueOf(value)
}
