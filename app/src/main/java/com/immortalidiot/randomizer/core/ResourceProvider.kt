package com.immortalidiot.randomizer.core

import android.content.Context
import com.immortalidiot.randomizer.ui.settings.Settings
import com.immortalidiot.randomizer.ui.theme.ThemePreference

interface ResourceProvider {
    fun getString(resId: Int): String
    suspend fun saveTheme(themePreference: ThemePreference)
    suspend fun loadTheme(): ThemePreference?
    suspend fun saveInitialScreen(route: String)
    suspend fun loadInitialScreen(): String?
    suspend fun saveRangeDefaults(first: Long, second: Long)
    suspend fun loadRangeDefaults(): List<Long>?
}

class ResourceProviderImpl(private val context: Context) : ResourceProvider {
    override fun getString(resId: Int): String = context.getString(resId)

    override suspend fun loadTheme(): ThemePreference? {
        return Settings.loadThemePreference(context)
    }

    override suspend fun saveTheme(themePreference: ThemePreference) {
        return Settings.saveThemePreference(context, themePreference)
    }

    override suspend fun saveInitialScreen(route: String) {
        return Settings.saveInitialScreen(context, route)
    }

    override suspend fun loadInitialScreen(): String? {
        return Settings.loadInitialScreen(context)
    }

    override suspend fun saveRangeDefaults(first: Long, second: Long) {
        return Settings.saveRangeDefaults(context, first, second)
    }

    override suspend fun loadRangeDefaults(): List<Long>? {
        return Settings.loadRangeDefaults(context)
    }
}
