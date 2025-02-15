package com.immortalidiot.randomizer.ui.about

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.immortalidiot.randomizer.ui.RandomNavigation

private val ABOUT_ROUTE = RandomNavigation.ApplicationInfoRoute.route
fun NavGraphBuilder.aboutDestination() {
    composable(route = ABOUT_ROUTE) {
        AboutScreen()
    }
}

fun NavController.navigateToAboutScreen() {
    navigate(route = ABOUT_ROUTE) {
        launchSingleTop = true
    }
}
