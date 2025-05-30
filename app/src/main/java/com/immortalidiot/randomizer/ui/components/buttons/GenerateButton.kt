package com.immortalidiot.randomizer.ui.components.buttons

import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.immortalidiot.randomizer.R

@Composable
fun GenerateButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Button(
        modifier = modifier.height(48.dp),
        onClick = onClick,
    ) {
        Text(
            text = stringResource(R.string.generate_btn).uppercase(),
            textAlign = TextAlign.Center
        )
    }
}
