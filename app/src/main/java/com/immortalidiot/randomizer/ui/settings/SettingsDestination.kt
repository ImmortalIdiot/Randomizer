package com.immortalidiot.randomizer.ui.settings

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.immortalidiot.randomizer.ui.RandomNavigation

private val SETTINGS_ROUTE = RandomNavigation.SettingsRoute.route

fun NavGraphBuilder.settingsDestination() {
    composable(route = SETTINGS_ROUTE) {
        SettingsScreen()
    }
}

fun NavController.navigateToSettings() {
    navigate(route = SETTINGS_ROUTE) {
        launchSingleTop = true
    }
}
