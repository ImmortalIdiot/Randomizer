package com.immortalidiot.randomizer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.immortalidiot.randomizer.core.ResourceProvider
import com.immortalidiot.randomizer.ui.RandomNavGraph
import com.immortalidiot.randomizer.ui.components.bar.BottomNavigationBar
import com.immortalidiot.randomizer.ui.components.bar.RandomizerAppBar
import com.immortalidiot.randomizer.ui.components.snackbar.LocalSnackbarHostState
import com.immortalidiot.randomizer.ui.settings.Settings
import com.immortalidiot.randomizer.ui.theme.LocalThemeChangeProvider
import com.immortalidiot.randomizer.ui.theme.LocalThemePreferenceProvider
import com.immortalidiot.randomizer.ui.theme.RandomizerTheme
import com.immortalidiot.randomizer.ui.theme.ThemePreference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val resourceProvider: ResourceProvider by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val expanded = remember { mutableStateOf(false) }
            val snackbarHostState = remember { SnackbarHostState() }

            var currentTheme: ThemePreference by rememberSaveable {
                val initial = runBlocking { resourceProvider.loadTheme() }
                    ?: ThemePreference.SYSTEM
                mutableStateOf(initial)
            }

            val onThemeChange: (ThemePreference) -> Unit = { newTheme ->
                currentTheme = newTheme
                CoroutineScope(Dispatchers.IO).launch {
                    resourceProvider.saveTheme(newTheme)
                }
            }

            LaunchedEffect(Unit) {
                val savedTheme = Settings.loadThemePreference(this@MainActivity)
                currentTheme = savedTheme ?: ThemePreference.SYSTEM
            }

            CompositionLocalProvider(
                LocalSnackbarHostState provides snackbarHostState,
                LocalThemePreferenceProvider provides currentTheme,
                LocalThemeChangeProvider provides onThemeChange
            ) {
                RandomizerTheme(themePreference = currentTheme) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            RandomizerAppBar(
                                navController = navController,
                                isExpanded = expanded.value,
                                onMenuClick = { expanded.value = true },
                                onDismissMenu = { expanded.value = false },
                            )
                        },
                        bottomBar = { BottomNavigationBar(navController = navController) }
                    ) { innerPadding ->
                        RandomNavGraph(
                            modifier = Modifier.padding(innerPadding),
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}
