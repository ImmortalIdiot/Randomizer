package com.immortalidiot.randomizer.ui.providers

import androidx.compose.runtime.staticCompositionLocalOf
import com.immortalidiot.randomizer.ui.Routes

val LocalInitialScreenProvider = staticCompositionLocalOf {
    Routes.RANGE_ROUTE
}

val LocalInitialScreenChangeProvider = staticCompositionLocalOf<(String) -> Unit> {
    error("No initial screen change handler provided")
}
