package com.immortalidiot.randomizer.ui.components.buttons

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign

@Composable
fun ButtonWithTextRes(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    textRes: Int
) {
    Button(
        modifier = modifier,
        onClick = onClick,
    ) {
        Text(
            text = stringResource(textRes),
            textAlign = TextAlign.Center
        )
    }
}
