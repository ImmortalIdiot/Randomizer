package com.immortalidiot.randomizer.ui.settings

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.immortalidiot.randomizer.ui.Routes

fun NavGraphBuilder.settingsDestination() {
    composable(route = Routes.SETTINGS_ROUTE) {
        SettingsScreen()
    }
}

fun NavController.navigateToSettings() {
    navigate(route = Routes.SETTINGS_ROUTE) {
        launchSingleTop = true
    }
}
