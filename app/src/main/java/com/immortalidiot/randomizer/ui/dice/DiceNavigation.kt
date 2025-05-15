package com.immortalidiot.randomizer.ui.dice

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.immortalidiot.randomizer.ui.Routes

fun NavGraphBuilder.diceDestination(
    navController: NavHostController
) {
    composable(route = Routes.DICE_ROUTE) {
        DiceScreen(navController = navController)
    }
}
