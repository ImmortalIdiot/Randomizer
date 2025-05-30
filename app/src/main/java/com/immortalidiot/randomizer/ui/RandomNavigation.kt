package com.immortalidiot.randomizer.ui

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ListAlt
import androidx.compose.material.icons.rounded.Casino
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Looks4
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector
import com.immortalidiot.randomizer.R

val bottomBarRoutes = listOf(Routes.RANGE_ROUTE, Routes.LIST_ROUTE, Routes.DICE_ROUTE)

object Routes {
    val RANGE_ROUTE = RandomNavigation.RangeRoute.route
    val LIST_ROUTE = RandomNavigation.ListRoute.route
    val DICE_ROUTE = RandomNavigation.DiceRoute.route
    val HISTORY_ROUTE = RandomNavigation.HistoryRoute.route
    val SETTINGS_ROUTE = RandomNavigation.SettingsRoute.route
    val APPLICATION_INFO_ROUTE = RandomNavigation.ApplicationInfoRoute.route
}

@Immutable
private sealed class RandomNavigation(val route: String) {
    data object RangeRoute : RandomNavigation("range")
    data object ListRoute : RandomNavigation("list")
    data object DiceRoute : RandomNavigation("dice")
    data object HistoryRoute : RandomNavigation("history")
    data object SettingsRoute : RandomNavigation("settings")
    data object ApplicationInfoRoute : RandomNavigation("about")
}

@Immutable
data class RandomDestination(
    @StringRes val label: Int,
    val icon: ImageVector,
    val route: String
)

val navigationItems = listOf(
    RandomDestination(
        label = R.string.range,
        icon = Icons.Rounded.Looks4,
        route = RandomNavigation.RangeRoute.route
    ),
    RandomDestination(
        label = R.string.list,
        icon = Icons.AutoMirrored.Rounded.ListAlt,
        route = RandomNavigation.ListRoute.route
    ),
    RandomDestination(
        label = R.string.dice,
        icon = Icons.Rounded.Casino,
        route = RandomNavigation.DiceRoute.route
    )
)

val appBarMenuItems = listOf(
    RandomDestination(
        label = R.string.history,
        icon = Icons.Rounded.History,
        route = Routes.HISTORY_ROUTE
    ),
    RandomDestination(
        label = R.string.settings,
        icon = Icons.Rounded.Settings,
        route = Routes.SETTINGS_ROUTE
    ),
    RandomDestination(
        label = R.string.application_info,
        icon = Icons.Rounded.Info,
        route = Routes.APPLICATION_INFO_ROUTE
    )
)