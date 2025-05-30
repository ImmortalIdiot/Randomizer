package com.immortalidiot.randomizer.ui.settings

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.immortalidiot.randomizer.R
import com.immortalidiot.randomizer.core.FIRST_VALUE_DEFAULT
import com.immortalidiot.randomizer.core.ResourceProvider
import com.immortalidiot.randomizer.core.SECOND_VALUE_DEFAULT
import com.immortalidiot.randomizer.core.UI_STATE_DELAY
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingsScreenViewModel(
    private val resourceProvider: ResourceProvider
) : ViewModel() {
    private val _uiState = MutableStateFlow<SettingsScreenUiState>(SettingsScreenUiState.Init)
    val uiState: StateFlow<SettingsScreenUiState> = _uiState.asStateFlow()

    private val _first = MutableStateFlow(FIRST_VALUE_DEFAULT)
    val first: StateFlow<String> = _first.asStateFlow()

    private val _second = MutableStateFlow(SECOND_VALUE_DEFAULT)
    val second: StateFlow<String> = _second.asStateFlow()

    init {
        viewModelScope.launch {
            val pair = resourceProvider.loadRangeDefaults()
            _first.value = pair?.get(0)?.toString() ?: FIRST_VALUE_DEFAULT
            _second.value = pair?.get(1)?.toString() ?: SECOND_VALUE_DEFAULT
        }
    }

    fun updateFirstField(newValue: String) {
        _first.value = newValue
    }

    fun updateSecondField(newValue: String) {
        _second.value = newValue
    }

    fun saveRangeDefaults() {
        val first = _first.value.toLongOrNull()
        val second = _second.value.toLongOrNull()

        val validationError = validateNumbers(first, second)
        if (validationError != null) {
            showError(validationError)
            return
        }

        viewModelScope.launch {
            resourceProvider.saveRangeDefaults(first!!, second!!)
            showSuccess(resourceProvider.getString(R.string.successful_saved))
        }
    }

    private fun validateNumbers(firstValue: Long?, secondValue: Long?): String? {
        if (firstValue == null) { return resourceProvider.getString(R.string.first_number) }
        if (secondValue == null) { return resourceProvider.getString(R.string.second_number) }

        return if (firstValue > secondValue) { resourceProvider.getString(R.string.range_values_error) }
        else null
    }

    private fun showError(message: String) {
        viewModelScope.launch {
            resetUiStateWithDelay()
            _uiState.value = SettingsScreenUiState.Error(message = message)
        }
    }

    private fun showSuccess(message: String) {
        viewModelScope.launch {
            resetUiStateWithDelay()
            _uiState.value = SettingsScreenUiState.Success(message = message, wasShown = false)
        }
    }

    private fun resetUiStateWithDelay() {
        viewModelScope.launch {
            delay(UI_STATE_DELAY)
            _uiState.value = SettingsScreenUiState.Init
        }
    }
}

@Immutable
sealed interface SettingsScreenUiState {
    data object Init : SettingsScreenUiState
    data class Error(val message: String) : SettingsScreenUiState
    data class Success(val message: String, val wasShown: Boolean) : SettingsScreenUiState
}
