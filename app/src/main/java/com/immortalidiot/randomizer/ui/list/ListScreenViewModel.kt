package com.immortalidiot.randomizer.ui.list

import androidx.compose.ui.focus.FocusRequester
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.immortalidiot.randomizer.R
import com.immortalidiot.randomizer.core.ResourceProvider
import com.immortalidiot.randomizer.core.UI_STATE_DELAY
import com.immortalidiot.randomizer.data.history.HistoryRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ListScreenViewModel(
    private val historyRepository: HistoryRepository,
    private val resourceProvider: ResourceProvider
) : ViewModel() {
    private val _uiState = MutableStateFlow<ListScreenUiState>(ListScreenUiState.Init)
    val uiState: StateFlow<ListScreenUiState> = _uiState.asStateFlow()

    private val _textFields = MutableStateFlow(listOf("", ""))
    val textFields: StateFlow<List<String>> = _textFields.asStateFlow()

    private val _result = MutableStateFlow("")
    val result = _result.asStateFlow()

    private val _focusRequesters = MutableStateFlow(listOf(FocusRequester(), FocusRequester()))
    val focusRequesters: StateFlow<List<FocusRequester>> = _focusRequesters.asStateFlow()

    fun updateTextField(index: Int, value: String) {
        val currentList = _textFields.value.toMutableList()
        if (index >= currentList.size) {
            currentList.add(value)
        } else {
            currentList[index] = value
        }

        _textFields.update { currentList }
    }

    fun addNewTextField() {
        _textFields.value += ""
        _focusRequesters.value += FocusRequester()
    }

    fun generateRandomElementFromList() {
        val notEmptyElements = _textFields.value.filter { it.isNotEmpty() }
        val size = notEmptyElements.size

        if (size >= 2) {
            viewModelScope.launch {
                _result.value = notEmptyElements.random()
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
