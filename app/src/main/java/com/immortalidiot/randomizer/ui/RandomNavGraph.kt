package com.immortalidiot.randomizer.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.immortalidiot.randomizer.ui.dice.diceDestination
import com.immortalidiot.randomizer.ui.list.listDestination
import com.immortalidiot.randomizer.ui.range.rangeDestination

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
        rangeDestination(navController = navController)
        listDestination(navController = navController)
        diceDestination(navController = navController)
    }
}
