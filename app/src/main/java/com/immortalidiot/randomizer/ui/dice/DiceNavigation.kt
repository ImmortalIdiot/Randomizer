package com.immortalidiot.randomizer.ui.dice

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.immortalidiot.randomizer.ui.Routes
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.diceDestination() {
    composable(route = Routes.DICE_ROUTE) {
        val viewModel: DiceScreenViewModel = koinViewModel()
        DiceScreen(viewModel = viewModel)
    }
}
