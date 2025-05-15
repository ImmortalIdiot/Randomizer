package com.immortalidiot.randomizer.ui.about

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.immortalidiot.randomizer.ui.Routes

fun NavGraphBuilder.aboutDestination() {
    composable(route = Routes.APPLICATION_INFO_ROUTE) {
        AboutScreen()
    }
}
