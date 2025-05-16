package com.immortalidiot.randomizer.model

import com.immortalidiot.randomizer.data.Content
import com.immortalidiot.randomizer.data.history.History

object Mapper {
    fun toModel(entity: History): HistoryModel {
        val modelContent: List<String> = when (entity.content) {
            is Content.Range -> listOf(
                entity.content.first.toString(),
                entity.content.second.toString()
            )

            is Content.ContentList -> entity.content.items
            is Content.Dice -> listOf(entity.content.result.toString())
        }

        return HistoryModel(
            time = entity.time,
            contentType = entity.contentType,
            content = modelContent,
            result = entity.result
        )
    }

    fun toEntity(model: HistoryModel): History {
        return History(
            time = model.time,
            contentType = model.contentType,
            content = when (model.content.size) {
                1 -> Content.ContentList(model.content)
                2 -> Content.Range(
                    first = model.content[0].toLong(),
                    second = model.content[1].toLong()
                )

                else -> Content.Dice(model.content[0].toLong())
            },
            result = model.result
        )
    }
}
