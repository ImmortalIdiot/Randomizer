package com.immortalidiot.randomizer.ui.list

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.immortalidiot.randomizer.ui.RandomNavigation
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.listDestination() {
    composable(route = RandomNavigation.ListRoute.route) {
        val viewModel: ListScreenViewModel = koinViewModel()
        ListScreen(viewModel = viewModel)
    }
}
