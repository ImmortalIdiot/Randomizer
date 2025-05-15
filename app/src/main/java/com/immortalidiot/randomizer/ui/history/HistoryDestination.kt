package com.immortalidiot.randomizer.ui.history

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.immortalidiot.randomizer.ui.Routes

fun NavGraphBuilder.historyDestination() {
    composable(route = Routes.HISTORY_ROUTE) {
        HistoryScreen()
    }
}

fun NavController.navigateToHistory() {
    navigate(route = Routes.HISTORY_ROUTE) {
        launchSingleTop = true
    }
}
