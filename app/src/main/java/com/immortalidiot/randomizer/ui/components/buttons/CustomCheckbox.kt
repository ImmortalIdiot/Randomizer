package com.immortalidiot.randomizer.ui.components.buttons

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckBox
import androidx.compose.material.icons.rounded.CheckBoxOutlineBlank
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.immortalidiot.randomizer.ui.theme.PrimaryButtonColor
import com.immortalidiot.randomizer.ui.theme.RandomizerTheme

@Composable
fun CustomCheckBox(
    isSelectedOne: Boolean,
    checked: Boolean,
    onChecked: (Boolean) -> Unit,
    iconSize: Dp = 20.dp
) {
    AnimatedVisibility(
        visible = isSelectedOne,
        enter = scaleIn(initialScale = 0.5f),
        exit = shrinkOut(shrinkTowards = Alignment.Center)
    ) {
        Icon(
            imageVector = if (checked) Icons.Rounded.CheckBox else Icons.Rounded.CheckBoxOutlineBlank,
            modifier = Modifier.size(iconSize ).clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { onChecked(checked) }
            ),
            contentDescription = "",
            tint = if (checked) PrimaryButtonColor else Color.Gray
        )
    }
}

@Preview
@Composable
private fun CheckboxPreview() {
    RandomizerTheme {
        Scaffold { padding ->
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                var checked by remember { mutableStateOf(false) }
                CustomCheckBox(
                    isSelectedOne = true,
                    checked = checked,
                    onChecked = { checked = !checked },
                    iconSize = 48.dp
                )
            }
        }
    }
}
