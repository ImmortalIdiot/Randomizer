package com.immortalidiot.randomizer.ui.list

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.immortalidiot.randomizer.ui.Routes
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.listDestination() {
    composable(route = Routes.LIST_ROUTE) {
        val viewModel: ListScreenViewModel = koinViewModel()
        ListScreen(viewModel = viewModel)
    }
}
