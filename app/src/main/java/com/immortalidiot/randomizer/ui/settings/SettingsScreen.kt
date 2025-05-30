package com.immortalidiot.randomizer.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.immortalidiot.randomizer.R
import com.immortalidiot.randomizer.ui.Routes
import com.immortalidiot.randomizer.ui.components.field.RangeDefaultsTextField
import com.immortalidiot.randomizer.ui.components.snackbar.LocalSnackbarHostState
import com.immortalidiot.randomizer.ui.components.snackbar.CustomSnackbar
import com.immortalidiot.randomizer.ui.components.snackbar.showMessage
import com.immortalidiot.randomizer.ui.providers.LocalInitialScreenChangeProvider
import com.immortalidiot.randomizer.ui.providers.LocalInitialScreenProvider
import com.immortalidiot.randomizer.ui.providers.LocalThemeChangeProvider
import com.immortalidiot.randomizer.ui.providers.LocalThemePreferenceProvider
import com.immortalidiot.randomizer.ui.theme.BrightLightGreen
import com.immortalidiot.randomizer.ui.theme.PrimaryButtonColor
import com.immortalidiot.randomizer.ui.theme.ThemePreference

@Composable
fun SettingsScreen(
    viewModel: SettingsScreenViewModel
) {
    val onThemeChange = LocalThemeChangeProvider.current
    val themePreference = LocalThemePreferenceProvider.current
    val onInitialScreenChange = LocalInitialScreenChangeProvider.current
    val initialScreen = LocalInitialScreenProvider.current

    val snackbarHostState = LocalSnackbarHostState.current

    val uiState by viewModel.uiState.collectAsState()
    val first = viewModel.first.collectAsState()
    val second = viewModel.second.collectAsState()

    val themeButtons = listOf(
        ThemeButtonContent(
            ThemePreference.LIGHT,
            R.string.light_theme,
            themePreference == ThemePreference.LIGHT
        ),
        ThemeButtonContent(
            ThemePreference.DARK,
            R.string.dark_theme,
            themePreference == ThemePreference.DARK
        ),
        ThemeButtonContent(
            ThemePreference.SYSTEM,
            R.string.system_theme,
            themePreference == ThemePreference.SYSTEM
        ),
    )

    val initialScreenButtons = listOf(
        InitialScreenButtonContent(
            Routes.RANGE_ROUTE,
            R.string.range,
            initialScreen == Routes.RANGE_ROUTE
        ),
        InitialScreenButtonContent(
            Routes.LIST_ROUTE,
            R.string.list,
            initialScreen == Routes.LIST_ROUTE
        ),
        InitialScreenButtonContent(
            Routes.DICE_ROUTE,
            R.string.dice,
            initialScreen == Routes.DICE_ROUTE
        ),
    )

    val buttonItemHeight = ButtonDefaults.MinHeight + 8.dp
    val primaryButtonColor = Color(0xFF9DCBFF)

    val saveButtonColors = ButtonDefaults.buttonColors(
        containerColor = PrimaryButtonColor,
        disabledContainerColor = PrimaryButtonColor,
        contentColor = MaterialTheme.colorScheme.surface,
        disabledContentColor = MaterialTheme.colorScheme.surface
    )

    LaunchedEffect(uiState) {
        if (uiState is SettingsScreenUiState.Error) {
            val toastText = (uiState as SettingsScreenUiState.Error).message
            snackbarHostState.showMessage(toastText)
        }
        if (uiState is SettingsScreenUiState.Success) {
            val toastText = (uiState as SettingsScreenUiState.Success).message
            snackbarHostState.showMessage(toastText)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopStart)
                .padding(start = 16.dp, top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Header(R.string.theme)
            Row(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = primaryButtonColor,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clip(RoundedCornerShape(16.dp)),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                themeButtons.forEach { (themePref, textRes, isActive) ->
                    Box(
                        modifier = Modifier
                            .width(108.dp)
                            .height(buttonItemHeight)
                            .background(
                                if (isActive) {
                                    primaryButtonColor
                                } else {
                                    Color.Transparent
                                }
                            )
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                onThemeChange(themePref)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(textRes),
                            color = if (isActive) {
                                MaterialTheme.colorScheme.surface
                            } else {
                                primaryButtonColor
                            }
                        )
                    }
                }
            }

            VerticalSpacer()

            Header(R.string.initial_screen)
            Row(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = primaryButtonColor,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clip(RoundedCornerShape(16.dp)),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                initialScreenButtons.forEach { (route, textRes, isActive) ->
                    Box(
                        modifier = Modifier
                            .width(108.dp)
                            .height(buttonItemHeight)
                            .background(
                                if (isActive) {
                                    primaryButtonColor
                                } else {
                                    Color.Transparent
                                }
                            )
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                onInitialScreenChange(route)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(textRes),
                            color = if (isActive) {
                                MaterialTheme.colorScheme.surface
                            } else {
                                primaryButtonColor
                            }
                        )
                    }
                }
            }

            VerticalSpacer()

            Header(R.string.range_defaults)

            RangeDefaultsTextField(
                value = first.value,
                onValueChanged = {
                    viewModel.updateFirstField(it)
                },
                modifier = Modifier.fillMaxWidth().padding(end = 64.dp)
            )
            RangeDefaultsTextField(
                value = second.value,
                onValueChanged = {
                    viewModel.updateSecondField(it)
                },
                modifier = Modifier.fillMaxWidth().padding(end = 64.dp)
            )
            Button(
                modifier = Modifier.height(48.dp),
                onClick = {
                    viewModel.saveRangeDefaults()
                },
                colors = saveButtonColors
            ) {
                Text(text = stringResource(R.string.save).uppercase())
            }
        }
        CustomSnackbar(
            snackbarHostState = snackbarHostState,
            color = if (uiState !is SettingsScreenUiState.Error) BrightLightGreen else Color.Red,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Composable
private fun VerticalSpacer() {
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
private fun Header(
    textRes: Int
) {
    Text(
        modifier = Modifier.padding(start = 8.dp),
        text = stringResource(textRes),
        fontSize = 20.sp
    )
}

private data class ThemeButtonContent(
    val themePreference: ThemePreference,
    val buttonTextRes: Int,
    val isActive: Boolean
)

private data class InitialScreenButtonContent(
    val route: String,
    val textRes: Int,
    val isActive: Boolean
)
