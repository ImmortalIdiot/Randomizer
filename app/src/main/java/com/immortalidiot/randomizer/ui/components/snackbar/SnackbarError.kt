package com.immortalidiot.randomizer.ui.components.snackbar

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.immortalidiot.randomizer.ui.theme.RandomizerTheme


val LocalSnackbarHostState = compositionLocalOf<SnackbarHostState> {
    error("Snackbar is not initialized")
}

@Stable
suspend fun SnackbarHostState.showMessage(message: String) {
    showSnackbar(
        message = message,
        withDismissAction = true,
        duration = SnackbarDuration.Short
    )
}

@Composable
fun ErrorSnackbar(
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(
                bottom = 16.dp,
                start = 8.dp,
                end = 8.dp
            )
    ) {
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = modifier.align(Alignment.BottomCenter)
        ) { snackbarData ->
            Snackbar(
                contentColor = MaterialTheme.colorScheme.onSurface,
                containerColor = MaterialTheme.colorScheme.errorContainer,
                actionContentColor = MaterialTheme.colorScheme.onSurface,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = snackbarData.visuals.message,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleSmall
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = { snackbarData.dismiss() }) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = ""
                        )
                    }
                }
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
                ErrorSnackbar(snackbarHostState = snackbarHostState)
            }
        }
    }
}
