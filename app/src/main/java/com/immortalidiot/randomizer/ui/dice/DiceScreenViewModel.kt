package com.immortalidiot.randomizer.ui.dice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.immortalidiot.randomizer.data.ContentType
import com.immortalidiot.randomizer.data.history.HistoryRepository
import com.immortalidiot.randomizer.data.model.HistoryModel
import com.immortalidiot.randomizer.data.model.Mapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class DiceScreenViewModel(
    private val historyRepository: HistoryRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<DiceScreenUiState>(DiceScreenUiState.Init)
    val uiState: StateFlow<DiceScreenUiState> = _uiState.asStateFlow()

    private val _result = MutableStateFlow("")
    val result: StateFlow<String> = _result.asStateFlow()

    fun generate() {
        _result.value = (1..6).random().toString()
        _uiState.value = DiceScreenUiState.Generated
        viewModelScope.launch {
            saveToHistory()
        }
    }

    private suspend fun saveToHistory() {
        val history = HistoryModel(
            time = LocalDateTime.now(),
            contentType = ContentType.DICE,
            content = listOf("1..6"),
            result = _result.value
        )
        historyRepository.saveHistory(Mapper.toEntity(history))
    }
}

sealed interface DiceScreenUiState {
    data object Init : DiceScreenUiState
    data object Generated : DiceScreenUiState
}
