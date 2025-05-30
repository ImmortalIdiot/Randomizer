package com.immortalidiot.randomizer.ui.providers

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.compositionLocalOf

val LocalSnackbarHostState = compositionLocalOf<SnackbarHostState> {
    error("Snackbar is not initialized")
}
