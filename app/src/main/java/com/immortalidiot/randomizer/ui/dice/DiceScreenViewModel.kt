package com.immortalidiot.randomizer.ui.dice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.immortalidiot.randomizer.R
import com.immortalidiot.randomizer.data.ContentType
import com.immortalidiot.randomizer.data.history.HistoryRepository
import com.immortalidiot.randomizer.data.model.HistoryModelFabric
import com.immortalidiot.randomizer.data.model.Mapper
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DiceScreenViewModel(
    private val historyRepository: HistoryRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<DiceScreenUiState>(DiceScreenUiState.Init)

    private var diceFaces = listOf(
        R.drawable.dice_1,
        R.drawable.dice_2,
        R.drawable.dice_3,
        R.drawable.dice_4,
        R.drawable.dice_5,
        R.drawable.dice_6
    )

    private val default = R.drawable.dice_random

    private val _currentFace = MutableStateFlow(default)
    val currentFace: StateFlow<Int> = _currentFace.asStateFlow()

    fun rollDice() {
        if (_uiState.value is DiceScreenUiState.IsRolling) return

        _uiState.value = DiceScreenUiState.IsRolling

        viewModelScope.launch {
            val totalFrames = 60
            val delayMs = 20L

            repeat(totalFrames) {
                _currentFace.value = diceFaces.random()
                delay(delayMs)
            }


            _currentFace.value = diceFaces.random()
            _uiState.value = DiceScreenUiState.Generated

            saveToHistory()
        }
    }

    private suspend fun saveToHistory() {
        val history = HistoryModelFabric.createHistory(
            contentType = ContentType.DICE,
            content = listOf("1..6"),
            result = (diceFaces.indexOf(_currentFace.value) + 1).toString()
        )
        historyRepository.saveHistory(Mapper.toEntity(history))
    }
}

private sealed interface DiceScreenUiState {
    data object Init : DiceScreenUiState
    data object IsRolling : DiceScreenUiState
    data object Generated : DiceScreenUiState
}
