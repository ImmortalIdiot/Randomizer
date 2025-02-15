package com.immortalidiot.randomizer.ui.range

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.immortalidiot.randomizer.data.Content
import com.immortalidiot.randomizer.data.ContentType
import com.immortalidiot.randomizer.data.history.History
import com.immortalidiot.randomizer.data.history.HistoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class RangeScreenViewModel(
    private val historyRepository: HistoryRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<RangeScreenUiState>(RangeScreenUiState.Init)
    val uiState: StateFlow<RangeScreenUiState> = _uiState.asStateFlow()

    private val _firstField = MutableStateFlow("")
    val firstField = _firstField.asStateFlow()

    private val _secondField = MutableStateFlow("")
    val secondField = _secondField.asStateFlow()

    private val _result = MutableStateFlow("")
    val result = _result.asStateFlow()

    fun updateFirstField(newValue: String) {
        viewModelScope.launch {
            _firstField.emit(value = newValue)
        }
    }

    fun updateSecondField(newValue: String) {
        viewModelScope.launch {
            _secondField.emit(value = newValue)
        }
    }

    fun generateRandomNumberInRange(
        firstValue: String?,
        secondValue: String?
    ) {
        val first = firstValue?.toLongOrNull() ?: 1L
        val second = secondValue?.toLongOrNull() ?: 2L

        validateInputs(first, second)
        viewModelScope.launch {
            _uiState.emit(value = RangeScreenUiState.Generated)
            _result.emit((first..second).random().toString())
            saveToHistory(first = first, second = second, result = _result.value)
        }
    }

    private fun validateInputs(first: Long, second: Long) {
        if (first > second) {
            viewModelScope.launch {
                _uiState.emit(value = RangeScreenUiState.Error)
            }
        } else {
            viewModelScope.launch {
                _uiState.emit(value = RangeScreenUiState.Generated)
            }
        }
    }

    private suspend fun saveToHistory(
        first: Long,
        second: Long,
        result: String
    ) {
        val content = Content.Range(first = first, second = second)

        val history = History(
            time = LocalDateTime.now(),
            contentType = ContentType.RANGE,
            content = content,
            result = result
        )

        historyRepository.saveHistory(history = history)
    }
}

@Immutable
sealed interface RangeScreenUiState {
    data object Init : RangeScreenUiState
    data object Generated : RangeScreenUiState
    data object Error : RangeScreenUiState
}
