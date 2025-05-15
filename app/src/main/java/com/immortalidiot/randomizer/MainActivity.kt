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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.immortalidiot.randomizer.ui.RandomNavGraph
import com.immortalidiot.randomizer.ui.components.bar.BottomNavigationBar
import com.immortalidiot.randomizer.ui.components.bar.RandomizerAppBar
import com.immortalidiot.randomizer.ui.components.snackbar.LocalSnackbarHostState
import com.immortalidiot.randomizer.ui.theme.RandomizerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val expanded = remember { mutableStateOf(false) }
            val snackbarHostState = remember { SnackbarHostState() }

            CompositionLocalProvider(LocalSnackbarHostState provides snackbarHostState) {
                RandomizerTheme {
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
