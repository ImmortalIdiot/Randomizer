package com.immortalidiot.randomizer.ui.range

import android.content.Context
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.immortalidiot.randomizer.ui.RandomNavigation

fun NavGraphBuilder.rangeDestination(context: Context) {
    val route = context.getString(RandomNavigation.RangeRoute.route)

    composable(route = route) {
        RangeScreen()
    }
}
