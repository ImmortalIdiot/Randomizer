package com.immortalidiot.randomizer.ui.components.field

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign

@Composable
fun NumberInputField(
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChange: (String) -> Unit = {},
    placeholderText: String = "",
    isError: Boolean = false
) {
    val keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)

    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholderText,
                textAlign = TextAlign.Left
            )
        },
        isError =  isError,
        keyboardOptions = keyboardOptions,
        singleLine = true,
        maxLines = 1,
    )
}
