package com.immortalidiot.randomizer.ui.settings

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.immortalidiot.randomizer.ui.Routes
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.settingsDestination() {
    composable(route = Routes.SETTINGS_ROUTE) {
        val viewModel: SettingsScreenViewModel = koinViewModel()
        SettingsScreen(viewModel = viewModel)
    }
}
