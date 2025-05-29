package com.immortalidiot.randomizer.ui.history

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.immortalidiot.randomizer.data.history.HistoryRepository
import com.immortalidiot.randomizer.data.model.HistoryModel
import com.immortalidiot.randomizer.data.model.Mapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class HistoryScreenViewModel(
    private val historyRepository: HistoryRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<HistoryScreenUiState>(HistoryScreenUiState.Init)
    val uiState: StateFlow<HistoryScreenUiState> = _uiState.asStateFlow()

    private val _historyList = MutableStateFlow<List<HistoryModel>>(emptyList())
    val historyList: StateFlow<List<HistoryModel>> = _historyList.asStateFlow()

    private val _selectedOne = MutableStateFlow(false)
    val selectedOne: StateFlow<Boolean> = _selectedOne.asStateFlow()

    private val _deleteHistoryList = MutableStateFlow<List<HistoryModel>>(emptyList())

    init {
        viewModelScope.launch {
            _uiState.value = HistoryScreenUiState.Loading
            historyRepository
                .getAllHistory()
                .map { it.map(Mapper::toModel) }
                .onEach { history ->
                    _historyList.value = history
                    _uiState.value = HistoryScreenUiState.Loaded
                }
                .launchIn(this)
        }
    }

    fun toggleSelectedOne() {
        _selectedOne.value = !_selectedOne.value
    }

    fun deleteHistory() {
        viewModelScope.launch {
            historyRepository.deleteAllHistory()
        }
    }

    fun addItemToList(item: HistoryModel) {
        _deleteHistoryList.value += item
        Log.d("items", _deleteHistoryList.value.joinToString(", "))
    }

    fun removeItemFromList(item: HistoryModel) {
        _deleteHistoryList.value -= item
        Log.d("items", _deleteHistoryList.value.joinToString(", "))
    }

    fun deleteHistoryByList() {
        viewModelScope.launch {
            historyRepository.deleteByList(
                _deleteHistoryList.value.map { Mapper.toEntity(it) }
            )
            _deleteHistoryList.value = emptyList()
        }
    }
}

sealed interface HistoryScreenUiState {
    data object Init : HistoryScreenUiState
    data object Loading : HistoryScreenUiState
    data object Loaded : HistoryScreenUiState
}
