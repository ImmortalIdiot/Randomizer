package com.immortalidiot.randomizer.ui.history

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.immortalidiot.randomizer.ui.Routes
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.historyDestination() {
    composable(route = Routes.HISTORY_ROUTE) {
        val viewModel: HistoryScreenViewModel = koinViewModel()
        HistoryScreen(viewModel = viewModel)
    }
}
