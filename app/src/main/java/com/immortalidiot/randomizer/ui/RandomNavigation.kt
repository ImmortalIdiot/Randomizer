package com.immortalidiot.randomizer.ui

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import com.immortalidiot.randomizer.R

@Immutable
sealed class RandomNavigation(@StringRes val route: Int) {
    data object RangeRoute : RandomNavigation(R.string.range)
    data object ListRoute : RandomNavigation(R.string.list)
    data object DiceRoute : RandomNavigation(R.string.dice)
}
