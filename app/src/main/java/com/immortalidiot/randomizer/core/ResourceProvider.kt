package com.immortalidiot.randomizer.core

import android.content.Context
import com.immortalidiot.randomizer.ui.settings.Settings
import com.immortalidiot.randomizer.ui.theme.ThemePreference

interface ResourceProvider {
    fun getString(resId: Int): String
    suspend fun saveTheme(themePreference: ThemePreference)
    suspend fun loadTheme(): ThemePreference?
}

class ResourceProviderImpl(private val context: Context) : ResourceProvider {
    override fun getString(resId: Int): String = context.getString(resId)

    override suspend fun loadTheme(): ThemePreference? {
        return Settings.loadThemePreference(context)
    }

    override suspend fun saveTheme(themePreference: ThemePreference) {
        return Settings.saveThemePreference(context, themePreference)
    }
}
