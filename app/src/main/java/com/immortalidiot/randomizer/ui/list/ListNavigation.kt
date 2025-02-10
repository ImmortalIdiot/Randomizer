package com.immortalidiot.randomizer.ui.list

import android.content.Context
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.immortalidiot.randomizer.ui.RandomNavigation

fun NavGraphBuilder.listDestination(context: Context) {
    val route = context.getString(RandomNavigation.ListRoute.route)

    composable(route = route) {
        ListScreen()
    }
}
