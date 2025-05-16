package com.immortalidiot.randomizer.ui.dice

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.immortalidiot.randomizer.R
import com.immortalidiot.randomizer.ui.components.button.GenerateButton
import com.immortalidiot.randomizer.ui.components.field.UnderlineEmptyText

@Composable
fun DiceScreen(
    viewModel: DiceScreenViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val result by viewModel.result.collectAsState()

    val resultStyle = MaterialTheme.typography.headlineSmall

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = if (uiState is DiceScreenUiState.Init) {
                stringResource(R.string.to_generate)
            } else {
                stringResource(R.string.result)
            },
            style = resultStyle
        )
        ScreenSpacer()
        UnderlineEmptyText(
            modifier = modifier.height(64.dp),
            text = result,
        )
        ScreenSpacer()
        GenerateButton(onClick = remember { { viewModel.generate() } })
    }
}

@Composable
private fun ScreenSpacer() {
    Spacer(modifier = Modifier.height(16.dp))
}
