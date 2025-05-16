package com.immortalidiot.randomizer.model

import com.immortalidiot.randomizer.data.Content
import com.immortalidiot.randomizer.data.history.History

object Mapper {
    private const val DICE_CONTENT_STRING = "1..6"

    fun toModel(entity: History): HistoryModel {
        val modelContent: List<String> = when (val content = entity.content) {
            is Content.Range -> listOf(
                content.first.toString(),
                content.second.toString()
            )

            is Content.ContentList -> content.items
            is Content.Dice -> listOf(DICE_CONTENT_STRING)
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
            content = when {
                model.content.size == 2 -> {
                    val first = model.content[0].toLongOrNull()
                    val second = model.content[1].toLongOrNull()

                    if (first != null && second != null) { Content.Range(first, second) }
                    else { Content.ContentList(model.content) }
                }

                model.content == listOf(DICE_CONTENT_STRING) -> Content.Dice

                else -> Content.ContentList(model.content)
            },
            result = model.result
        )
    }
}
