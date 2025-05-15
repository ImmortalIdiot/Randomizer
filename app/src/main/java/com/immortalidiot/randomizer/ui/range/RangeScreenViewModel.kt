package com.immortalidiot.randomizer.ui.range

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.immortalidiot.randomizer.R
import com.immortalidiot.randomizer.core.ResourceProvider
import com.immortalidiot.randomizer.core.UI_STATE_DELAY
import com.immortalidiot.randomizer.data.Content
import com.immortalidiot.randomizer.data.ContentType
import com.immortalidiot.randomizer.data.history.History
import com.immortalidiot.randomizer.data.history.HistoryRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class RangeScreenViewModel(
    private val historyRepository: HistoryRepository,
    private val resourceProvider: ResourceProvider
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
        _firstField.value = newValue
    }

    fun updateSecondField(newValue: String) {
        _secondField.value = newValue
    }

    fun generateRandomNumberInRange(
        firstValue: String?,
        secondValue: String?,
    ) {
        val first = firstValue?.takeIf { it.isNotEmpty() }?.toLongOrNull() ?: 1L
        val second = secondValue?.takeIf { it.isNotEmpty() }?.toLongOrNull() ?: 2L

        _firstField.value = first.toString()
        _secondField.value = second.toString()

        if (!validateInputs(first, second)) {
            return
        }

        viewModelScope.launch {
            _uiState.value = RangeScreenUiState.Generated
            _result.value = (first..second).random().toString()
            saveToHistory(first = first, second = second, result = _result.value)
        }
    }

    private fun validateInputs(first: Long, second: Long): Boolean {
        return if (first > second) {
            viewModelScope.launch {
                resetUiStateWithDelay()
                _uiState.value = RangeScreenUiState.Error(
                    errorMessage = resourceProvider.getString(R.string.range_values_error)
                )
            }
            false
        } else {
            true
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

    private fun resetUiStateWithDelay() {
        viewModelScope.launch {
            delay(UI_STATE_DELAY)
            _uiState.value = RangeScreenUiState.Init
        }
    }
}

@Immutable
sealed interface RangeScreenUiState {
    data object Init : RangeScreenUiState
    data object Generated : RangeScreenUiState
    data class Error(val errorMessage: String) : RangeScreenUiState
}
