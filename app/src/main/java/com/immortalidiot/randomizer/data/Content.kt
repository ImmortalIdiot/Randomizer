package com.immortalidiot.randomizer.data

import kotlinx.serialization.Serializable

@Serializable
sealed class Content {

    @Serializable
    data class Range(val first: Long, val second: Long) : Content()

    @Serializable
    data class ContentList(val items: List<String>) : Content()

    @Serializable
    data class Dice(val result: Long) : Content()
}

enum class ContentType {
    LIST,
    RANGE,
    DICE
}
