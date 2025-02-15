package com.immortalidiot.randomizer.ui.history

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.immortalidiot.randomizer.ui.RandomNavigation

private val HISTORY_ROUTE = RandomNavigation.HistoryRoute.route

fun NavGraphBuilder.historyDestination() {
    composable(route = HISTORY_ROUTE) {
        HistoryScreen()
    }
}

fun NavController.navigateToHistory() {
    navigate(route = HISTORY_ROUTE) {
        launchSingleTop = true
    }
}
