package com.immortalidiot.randomizer.ui.dice

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.immortalidiot.randomizer.ui.components.button.GenerateButton

@Composable
fun DiceScreen(
    viewModel: DiceScreenViewModel,
    modifier: Modifier = Modifier
) {
    val currentFace by viewModel.currentFace.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier.size(128.dp)
        ) {
            Image(
                painter = painterResource(currentFace),
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary)
            )
        }
        ScreenSpacer()
        GenerateButton(onClick = remember { { viewModel.rollDice() } })
    }
}

@Composable
private fun ScreenSpacer() {
    Spacer(modifier = Modifier.height(16.dp))
}
