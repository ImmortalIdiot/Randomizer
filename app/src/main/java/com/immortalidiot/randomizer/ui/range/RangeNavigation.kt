package com.immortalidiot.randomizer.ui.range

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.immortalidiot.randomizer.ui.RandomNavigation

fun NavGraphBuilder.rangeDestination(
    navController: NavHostController
) {
    composable(route = RandomNavigation.RangeRoute.route) {
        RangeScreen(navController = navController)
    }
}
