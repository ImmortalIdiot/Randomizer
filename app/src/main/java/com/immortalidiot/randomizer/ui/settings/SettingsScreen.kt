package com.immortalidiot.randomizer.ui.settings

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.immortalidiot.randomizer.R
import com.immortalidiot.randomizer.ui.theme.LocalThemeChangeProvider
import com.immortalidiot.randomizer.ui.theme.ThemePreference

@Composable
fun SettingsScreen() {
    val onThemeChange = LocalThemeChangeProvider.current

    Row(
        modifier = Modifier
            .fillMaxSize()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = remember { { onThemeChange(ThemePreference.LIGHT) } }
        ) {
            Text(text = stringResource(R.string.light_theme))
        }
        Button(
            onClick = remember { { onThemeChange(ThemePreference.DARK) } }
        ) {
            Text(text = stringResource(R.string.dark_theme))
        }
        Button(
            onClick = remember { { onThemeChange(ThemePreference.SYSTEM) } }
        ) {
            Text(text = stringResource(R.string.system_theme))
        }
    }
}
