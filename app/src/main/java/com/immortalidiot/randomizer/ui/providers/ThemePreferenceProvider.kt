package com.immortalidiot.randomizer.ui.providers

import androidx.compose.runtime.staticCompositionLocalOf
import com.immortalidiot.randomizer.ui.theme.ThemePreference

val LocalThemePreferenceProvider = staticCompositionLocalOf {
    ThemePreference.SYSTEM
}

val LocalThemeChangeProvider = staticCompositionLocalOf<(ThemePreference) -> Unit> {
    error("onThemeChange is not provided")
}