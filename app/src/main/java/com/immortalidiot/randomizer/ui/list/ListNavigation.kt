package com.immortalidiot.randomizer.ui.list

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.immortalidiot.randomizer.ui.RandomNavigation

fun NavGraphBuilder.listDestination(
    navController: NavHostController
) {
    composable(route = RandomNavigation.ListRoute.route) {
        ListScreen(navController = navController)
    }
}
