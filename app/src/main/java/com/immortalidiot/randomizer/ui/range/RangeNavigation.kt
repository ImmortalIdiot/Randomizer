package com.immortalidiot.randomizer.ui.range

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.immortalidiot.randomizer.ui.RandomNavigation
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.rangeDestination() {
    composable(route = RandomNavigation.RangeRoute.route) {
        val viewModel: RangeScreenViewModel = koinViewModel()
        RangeScreen(viewModel = viewModel)
    }
}
