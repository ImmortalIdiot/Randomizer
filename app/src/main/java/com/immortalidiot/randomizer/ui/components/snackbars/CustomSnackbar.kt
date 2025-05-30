package com.immortalidiot.randomizer.ui.components.snackbars

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.immortalidiot.randomizer.ui.providers.LocalSnackbarHostState
import com.immortalidiot.randomizer.ui.theme.RandomizerTheme


@Stable
suspend fun SnackbarHostState.showMessage(message: String) {
    showSnackbar(
        message = message,
        withDismissAction = false,
        duration = SnackbarDuration.Short
    )
}

@Composable
fun CustomSnackbar(
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    color: Color
) {
    Box {
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = modifier.align(Alignment.BottomCenter)
        ) { snackbarData ->
            Snackbar(
                contentColor = Color.White,
                containerColor = color,
            ) {
                Text(
                    modifier = modifier.fillMaxWidth(),
                    text = snackbarData.visuals.message,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SnackbarPreview() {
    val snackbarHostState = remember { SnackbarHostState() }
    val showSnackbar = remember { mutableStateOf(false) }
    val message = remember { mutableStateOf(String()) }

    if (showSnackbar.value) {
        LaunchedEffect(Unit) {
            snackbarHostState.showMessage(message.value)
            showSnackbar.value = false
        }
    }

    CompositionLocalProvider(LocalSnackbarHostState provides snackbarHostState) {
        RandomizerTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
            ) {
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(32.dp)
                ) {
                    repeat(30) { iteration ->
                        item(key = iteration) {
                            val text = "${iteration + 1} text"
                            Text(
                                modifier = Modifier.clickable {
                                    message.value = text
                                    showSnackbar.value = true
                                },
                                text = text
                            )
                        }
                    }
                }
                CustomSnackbar(snackbarHostState = snackbarHostState, color = Color.Red)
            }
        }
    }
}
