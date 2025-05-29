package com.immortalidiot.randomizer.ui.components.animations

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavBackStackEntry
import com.immortalidiot.randomizer.ui.bottomBarRoutes

fun randomizerEnterTransition(
    initialState: NavBackStackEntry,
    targetState: NavBackStackEntry
): EnterTransition {
    val fromIndex = bottomBarRoutes.indexOf(initialState.destination.route)
    val toIndex = bottomBarRoutes.indexOf(targetState.destination.route)

    return if (fromIndex != -1 && toIndex != -1) {
        when {
            toIndex > fromIndex -> slideInHorizontally(initialOffsetX = { width -> width }) + fadeIn()
            toIndex < fromIndex -> slideInHorizontally(initialOffsetX = { width -> -width }) + fadeIn()
            else -> EnterTransition.None
        }
    } else {
        slideInHorizontally(initialOffsetX = { width -> width }) + fadeIn()
    }
}

fun randomizerExitTransition(
    initialState: NavBackStackEntry,
    targetState: NavBackStackEntry
): ExitTransition {
    val fromIndex = bottomBarRoutes.indexOf(initialState.destination.route)
    val toIndex = bottomBarRoutes.indexOf(targetState.destination.route)

    return if (fromIndex != -1 && toIndex != -1) {
        when {
            toIndex > fromIndex -> slideOutHorizontally(targetOffsetX = { width -> -width }) + fadeOut()
            toIndex < fromIndex -> slideOutHorizontally(targetOffsetX = { width -> width }) + fadeOut()
            else -> ExitTransition.None
        }
    } else {
        slideOutHorizontally(targetOffsetX = { width -> -width }) + fadeOut()
    }
}
