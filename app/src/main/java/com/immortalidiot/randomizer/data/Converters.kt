package com.immortalidiot.randomizer.data

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

object Converters {

    @TypeConverter
    fun localDateTimeToLong(localDateTime: LocalDateTime): Long =
        localDateTime.atZone(ZoneId.systemDefault()).toInstant().epochSecond

    @TypeConverter
    fun longToLocalDateTime(timestamp: Long): LocalDateTime =
        Instant.ofEpochSecond(timestamp).atZone(ZoneId.systemDefault()).toLocalDateTime()
}
