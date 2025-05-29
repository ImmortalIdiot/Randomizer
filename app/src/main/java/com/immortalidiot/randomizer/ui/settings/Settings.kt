package com.immortalidiot.randomizer.ui.settings

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.immortalidiot.randomizer.ui.theme.ThemePreference
import kotlinx.coroutines.flow.first

object Settings {
    private val KEY_THEME = stringPreferencesKey(name = "theme")
    private val KEY_INITIAL_SCREEN = stringPreferencesKey(name = "initial_screen")

    private val Context.settingsDatastore by preferencesDataStore(name = "settings")

    suspend fun saveThemePreference(context: Context, themePreference: ThemePreference) {
        context.settingsDatastore.edit {
            it[KEY_THEME] = themePreference.name
        }
    }

    suspend fun loadThemePreference(context: Context): ThemePreference? {
        val preference = context.settingsDatastore.data.first()
        val themePreference = preference[KEY_THEME]
        return if (themePreference != null) ThemePreference.valueOf(themePreference) else null
    }

    suspend fun saveInitialScreen(context: Context, route: String) {
        context.settingsDatastore.edit {
            it[KEY_INITIAL_SCREEN] = route
        }
    }

    suspend fun loadInitialScreen(context: Context): String? {
        val preference = context.settingsDatastore.data.first()
        return preference[KEY_INITIAL_SCREEN]
    }
}
