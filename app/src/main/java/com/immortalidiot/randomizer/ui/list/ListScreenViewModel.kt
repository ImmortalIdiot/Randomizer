package com.immortalidiot.randomizer.ui.list

import androidx.compose.runtime.mutableStateListOf
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

class ListScreenViewModel(
    private val historyRepository: HistoryRepository,
    private val resourceProvider: ResourceProvider
) : ViewModel() {
    private val _uiState = MutableStateFlow<ListScreenUiState>(ListScreenUiState.Init)
    val uiState: StateFlow<ListScreenUiState> = _uiState.asStateFlow()

    val textFields = mutableStateListOf("", "")

    private val _result = MutableStateFlow("")
    val result = _result.asStateFlow()

    fun updateTextField(index: Int, value: String) {
        if (index < textFields.size) {
            textFields[index] = value
        }
    }

    fun addNewTextField() {
        textFields.add("")
    }

    fun generateRandomElementFromList() {
        val notEmptyElements = textFields.filter { it.isNotEmpty() }
        val size = notEmptyElements.size

        if (size >= 2) {
            viewModelScope.launch {
                _result.value = notEmptyElements.random()
                saveToHistory(notEmptyElements, _result.value)
            }
        } else {
            viewModelScope.launch {
                _uiState.value = ListScreenUiState.Error(
                    resourceProvider.getString(R.string.list_error)
                )
                resetUiState()
            }
        }
    }

    private suspend fun saveToHistory(content: List<String>, result: String) {
        val history = HistoryModel(
            time = LocalDateTime.now(),
            contentType = ContentType.LIST,
            content = content,
            result = result
        )
        historyRepository.saveHistory(Mapper.toEntity(history))
    }

    private fun resetUiState() {
        viewModelScope.launch {
            delay(UI_STATE_DELAY)
            _uiState.value = ListScreenUiState.Init
        }
    }
}

sealed interface ListScreenUiState {
    data object Init : ListScreenUiState
    data class Error(val message: String) : ListScreenUiState
}
