package com.immortalidiot.randomizer.ui.range

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.immortalidiot.randomizer.R
import com.immortalidiot.randomizer.core.ResourceProvider
import com.immortalidiot.randomizer.core.UI_STATE_DELAY
import com.immortalidiot.randomizer.data.ContentType
import com.immortalidiot.randomizer.data.history.HistoryRepository
import com.immortalidiot.randomizer.data.model.HistoryModel
import com.immortalidiot.randomizer.data.model.Mapper
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime

private const val FIRST_VALUE_DEFAULT = "1"
private const val SECOND_VALUE_DEFAULT = "2"

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

    fun generateRandomNumberInRange() {
        if (_firstField.value.isEmpty() && _secondField.value.isEmpty()) {
            _firstField.value = FIRST_VALUE_DEFAULT
            _secondField.value = SECOND_VALUE_DEFAULT
        }

        val first = _firstField.value.toLongOrNull()
        val second = _secondField.value.toLongOrNull()

        val validationError = validateRange(first, second)
        if (validationError != null) {
            showError(validationError)
            return
        }

        viewModelScope.launch {
            _uiState.value = RangeScreenUiState.Generated
            _result.value = (first!!..second!!).random().toString()
            saveToHistory(first.toString(), second.toString(), _result.value)
        }
    }

    private fun validateRange(first: Long?, second: Long?): String? {
        if (first == null) { return resourceProvider.getString(R.string.first_number) }
        if (second == null) { return resourceProvider.getString(R.string.second_number) }

        return if (first > second) { resourceProvider.getString(R.string.range_values_error) }
               else null
    }

    private fun showError(message: String) {
        viewModelScope.launch {
            resetUiStateWithDelay()
            _uiState.value = RangeScreenUiState.Error(errorMessage = message)
        }
    }

    private suspend fun saveToHistory(
        first: String,
        second: String,
        result: String
    ) {
        val history = HistoryModel(
            id = 0L,
            time = LocalDateTime.now(),
            contentType = ContentType.RANGE,
            content = listOf(first, second),
            result = result
        )
        historyRepository.saveHistory(history = Mapper.toEntity(model = history))
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
