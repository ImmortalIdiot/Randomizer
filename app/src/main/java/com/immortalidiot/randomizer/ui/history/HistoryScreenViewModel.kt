package com.immortalidiot.randomizer.ui.history

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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HistoryScreenViewModel(
    private val historyRepository: HistoryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<HistoryScreenUiState>(HistoryScreenUiState.Init)
    val uiState: StateFlow<HistoryScreenUiState> = _uiState.asStateFlow()

    private val _historyList = MutableStateFlow<List<HistoryModel>>(emptyList())
    val historyList: StateFlow<List<HistoryModel>> = _historyList.asStateFlow()

    private val _selectedIds = MutableStateFlow<Set<Long>>(emptySet())
    val selectedIds: StateFlow<Set<Long>> = _selectedIds.asStateFlow()

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

    fun toggleSelection(id: Long) {
        _selectedIds.update { current ->
            if (id in current) current - id else current + id
        }
    }

    fun deleteHistory() {
        viewModelScope.launch {
            historyRepository.deleteAllHistory()
            clearSelection()
        }
    }

    private fun clearSelection() {
        _selectedIds.value = emptySet()
    }

    fun deleteSelectedHistory() {
        viewModelScope.launch {
            val itemsToDelete = _historyList.value.filter { it.id in _selectedIds.value }
            historyRepository.deleteByList(itemsToDelete.map { Mapper.toEntity(it) })
            clearSelection()
        }
    }
}

sealed interface HistoryScreenUiState {
    data object Init : HistoryScreenUiState
    data object Loading : HistoryScreenUiState
    data object Loaded : HistoryScreenUiState
}
