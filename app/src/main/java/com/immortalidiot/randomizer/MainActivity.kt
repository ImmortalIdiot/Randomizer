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
import com.immortalidiot.randomizer.ui.Routes
import com.immortalidiot.randomizer.ui.components.bar.BottomNavigationBar
import com.immortalidiot.randomizer.ui.components.bar.RandomizerAppBar
import com.immortalidiot.randomizer.ui.components.snackbar.LocalSnackbarHostState
import com.immortalidiot.randomizer.ui.providers.LocalInitialScreenChangeProvider
import com.immortalidiot.randomizer.ui.providers.LocalInitialScreenProvider
import com.immortalidiot.randomizer.ui.providers.LocalThemeChangeProvider
import com.immortalidiot.randomizer.ui.providers.LocalThemePreferenceProvider
import com.immortalidiot.randomizer.ui.settings.Settings
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

            val realInitialRoute =
                remember {
                    runBlocking { resourceProvider.loadInitialScreen() ?: Routes.RANGE_ROUTE }
                }

            var savedInitialRoute by rememberSaveable {
                mutableStateOf(realInitialRoute)
            }

            val onInitialScreenChange: (String) -> Unit = { newInitialRoute ->
                savedInitialRoute = newInitialRoute
                CoroutineScope(Dispatchers.IO).launch {
                    resourceProvider.saveInitialScreen(newInitialRoute)
                }
            }

            LaunchedEffect(Unit) {
                val savedTheme = Settings.loadThemePreference(this@MainActivity)
                currentTheme = savedTheme ?: ThemePreference.SYSTEM

                val route = Settings.loadInitialScreen(this@MainActivity)
                savedInitialRoute = route ?: Routes.RANGE_ROUTE
            }

            CompositionLocalProvider(
                LocalSnackbarHostState provides snackbarHostState,
                LocalThemePreferenceProvider provides currentTheme,
                LocalThemeChangeProvider provides onThemeChange,
                LocalInitialScreenProvider provides savedInitialRoute,
                LocalInitialScreenChangeProvider provides onInitialScreenChange
            ) {
                RandomizerTheme(themePreference = currentTheme) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = { RandomizerAppBar(navController = navController) },
                        bottomBar = { BottomNavigationBar(navController = navController) }
                    ) { innerPadding ->
                        RandomNavGraph(
                            modifier = Modifier.padding(innerPadding),
                            navController = navController,
                            entryPoint = realInitialRoute
                        )
                    }
                }
            }
        }
    }
}
