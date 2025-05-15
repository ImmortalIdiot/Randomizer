package com.immortalidiot.randomizer.ui.settings

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.immortalidiot.randomizer.ui.Routes

fun NavGraphBuilder.settingsDestination() {
    composable(route = Routes.SETTINGS_ROUTE) {
        SettingsScreen()
    }
}
