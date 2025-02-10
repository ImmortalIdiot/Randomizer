package com.immortalidiot.randomizer.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.immortalidiot.randomizer.ui.dice.diceDestination
import com.immortalidiot.randomizer.ui.list.listDestination
import com.immortalidiot.randomizer.ui.range.rangeDestination

@Composable
fun RandomNavGraph(
    navController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = context.getString(RandomNavigation.RangeRoute.route)
    ) {
        rangeDestination(context = context)
        listDestination(context = context)
        diceDestination(context = context)
    }
}
