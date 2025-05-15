package com.immortalidiot.randomizer.ui.range

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.immortalidiot.randomizer.ui.Routes
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.rangeDestination() {
    composable(route = Routes.RANGE_ROUTE) {
        val viewModel: RangeScreenViewModel = koinViewModel()
        RangeScreen(viewModel = viewModel)
    }
}
