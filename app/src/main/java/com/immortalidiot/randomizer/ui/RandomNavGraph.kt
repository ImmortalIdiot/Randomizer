package com.immortalidiot.randomizer.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.immortalidiot.randomizer.ui.about.aboutDestination
import com.immortalidiot.randomizer.ui.components.animations.randomizerEnterTransition
import com.immortalidiot.randomizer.ui.components.animations.randomizerExitTransition
import com.immortalidiot.randomizer.ui.dice.diceDestination
import com.immortalidiot.randomizer.ui.history.historyDestination
import com.immortalidiot.randomizer.ui.list.listDestination
import com.immortalidiot.randomizer.ui.range.rangeDestination
import com.immortalidiot.randomizer.ui.settings.settingsDestination

@Composable
fun RandomNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    entryPoint: String
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = entryPoint,
        enterTransition = { randomizerEnterTransition(initialState, targetState) },
        exitTransition = { randomizerExitTransition(initialState, targetState) }
    ) {
        rangeDestination()
        listDestination()
        diceDestination()
        historyDestination()
        settingsDestination()
        aboutDestination()
    }

}
