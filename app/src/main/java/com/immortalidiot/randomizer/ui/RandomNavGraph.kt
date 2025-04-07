package com.immortalidiot.randomizer.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.immortalidiot.randomizer.ui.about.aboutDestination
import com.immortalidiot.randomizer.ui.dice.diceDestination
import com.immortalidiot.randomizer.ui.history.historyDestination
import com.immortalidiot.randomizer.ui.list.listDestination
import com.immortalidiot.randomizer.ui.range.rangeDestination
import com.immortalidiot.randomizer.ui.settings.settingsDestination

@Composable
fun RandomNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = RandomNavigation.RangeRoute.route
    ) {
        rangeDestination()
        listDestination()
        diceDestination(navController = navController)
        // TODO: add "heads and tails"
        historyDestination()
        settingsDestination()
        aboutDestination()
    }
}
