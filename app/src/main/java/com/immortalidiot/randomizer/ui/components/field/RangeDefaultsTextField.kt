package com.immortalidiot.randomizer.ui.components.field

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun RangeDefaultsTextField(
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)

    TextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChanged,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent
        ),
        keyboardOptions = keyboardOptions,
        singleLine = true,
        maxLines = 1
    )
}
