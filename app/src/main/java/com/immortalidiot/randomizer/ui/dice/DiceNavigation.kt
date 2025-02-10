package com.immortalidiot.randomizer.ui.dice

import android.content.Context
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.immortalidiot.randomizer.ui.RandomNavigation

fun NavGraphBuilder.diceDestination(context: Context) {
    val route = context.getString(RandomNavigation.DiceRoute.route)

    composable(route = route) {
        DiceScreen()
    }
}
