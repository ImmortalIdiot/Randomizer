package com.immortalidiot.randomizer.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.immortalidiot.randomizer.data.history.History
import com.immortalidiot.randomizer.data.history.HistoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HistoryScreenViewModel(
    private val historyRepository: HistoryRepository
) : ViewModel() {
    private val _historyList = MutableStateFlow<List<History>>(emptyList())
    val historyList: StateFlow<List<History>> = _historyList.asStateFlow()

    init {
        viewModelScope.launch {
            historyRepository.getAllHistory().collectLatest { list ->
                _historyList.value = list
            }
        }
    }
}
