package com.immortalidiot.randomizer.ui

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ListAlt
import androidx.compose.material.icons.rounded.Casino
import androidx.compose.material.icons.rounded.Looks4
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector
import com.immortalidiot.randomizer.R

@Immutable
sealed class RandomNavigation(val route: String) {
    data object RangeRoute : RandomNavigation("range")
    data object ListRoute : RandomNavigation("list")
    data object DiceRoute : RandomNavigation("dice")
}

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
