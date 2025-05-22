package com.immortalidiot.randomizer.ui.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.immortalidiot.randomizer.R
import com.immortalidiot.randomizer.ui.theme.LocalThemeChangeProvider
import com.immortalidiot.randomizer.ui.theme.LocalThemePreferenceProvider
import com.immortalidiot.randomizer.ui.theme.ThemePreference

@Composable
fun SettingsScreen() {
    val onThemeChange = LocalThemeChangeProvider.current
    val themePreference = LocalThemePreferenceProvider.current

    val buttons = listOf(
        ButtonContent(
            ThemePreference.LIGHT,
            R.string.light_theme,
            themePreference == ThemePreference.LIGHT
        ),
        ButtonContent(
            ThemePreference.DARK,
            R.string.dark_theme,
            themePreference == ThemePreference.DARK
        ),
        ButtonContent(
            ThemePreference.SYSTEM,
            R.string.system_theme,
            themePreference == ThemePreference.SYSTEM
        ),
    )

    val themeItemHeight = ButtonDefaults.MinHeight + 8.dp
    val primaryThemeColor = Color(0xFF9DCBFF)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = stringResource(R.string.theme),
            fontSize = 20.sp
        )
        Row(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = primaryThemeColor,
                    shape = RoundedCornerShape(16.dp)
                )
                .clip(RoundedCornerShape(16.dp)),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            buttons.forEach { (themePref, textRes, isActive) ->
                Box(
                    modifier = Modifier
                        .width(108.dp)
                        .height(themeItemHeight)
                        .background(
                            if (isActive) {
                                primaryThemeColor
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
                            primaryThemeColor
                        }
                    )
                }
            }
        }
    }
}

private data class ButtonContent(
    val themePreference: ThemePreference,
    val buttonTextRes: Int,
    val isActive: Boolean
)
