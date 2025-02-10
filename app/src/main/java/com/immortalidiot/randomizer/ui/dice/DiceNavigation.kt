package com.immortalidiot.randomizer.ui.dice

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.immortalidiot.randomizer.ui.RandomNavigation

fun NavGraphBuilder.diceDestination(
    navController: NavHostController
) {
    composable(route = RandomNavigation.DiceRoute.route) {
        DiceScreen(navController = navController)
    }
}
