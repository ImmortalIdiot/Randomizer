package com.immortalidiot.randomizer.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.immortalidiot.randomizer.data.history.HistoryRepository
import com.immortalidiot.randomizer.data.model.HistoryModel
import com.immortalidiot.randomizer.data.model.Mapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HistoryScreenViewModel(
    private val historyRepository: HistoryRepository
) : ViewModel() {
    private val _historyList = MutableStateFlow<List<HistoryModel>>(emptyList())
    val historyList: StateFlow<List<HistoryModel>> = _historyList.asStateFlow()

    init {
        viewModelScope.launch {
            historyRepository.getAllHistory().collectLatest { list ->
                _historyList.value = list.map { Mapper.toModel(it) }
            }
        }
    }
}
