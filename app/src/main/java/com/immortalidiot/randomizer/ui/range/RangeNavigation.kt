package com.immortalidiot.randomizer.ui.range

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.immortalidiot.randomizer.ui.RandomNavigation

fun NavGraphBuilder.rangeDestination() {
    composable(route = RandomNavigation.RangeRoute.route) {
        RangeScreen()
    }
}
