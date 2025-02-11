package com.immortalidiot.randomizer.ui.components.field

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp

@Composable
fun UnderlineEmptyText(
    text: String,
    modifier: Modifier = Modifier,
) {
    val textIsBlank = text.isBlank()
    Box(modifier = modifier) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = if (textIsBlank) "" else text,
            style = MaterialTheme.typography.headlineMedium,
        )

        if (textIsBlank) {
            HorizontalDivider(
                modifier = Modifier
                    .width(128.dp)
                    .align(Alignment.BottomCenter),
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
