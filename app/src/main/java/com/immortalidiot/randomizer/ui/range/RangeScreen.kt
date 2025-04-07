package com.immortalidiot.randomizer.ui.range

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.immortalidiot.randomizer.R
import com.immortalidiot.randomizer.ui.components.button.GenerateButton
import com.immortalidiot.randomizer.ui.components.field.NumberInputField
import com.immortalidiot.randomizer.ui.components.field.UnderlineEmptyText
import com.immortalidiot.randomizer.ui.components.snackbar.ErrorSnackbar
import com.immortalidiot.randomizer.ui.components.snackbar.LocalSnackbarHostState
import com.immortalidiot.randomizer.ui.components.snackbar.showMessage
import com.immortalidiot.randomizer.ui.theme.RandomizerTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun RangeScreen(
    modifier: Modifier = Modifier,
    viewModel: RangeScreenViewModel
) {
    val resultStyle = MaterialTheme.typography.headlineSmall

    val uiState by viewModel.uiState.collectAsState()

    val firstField by viewModel.firstField.collectAsState()
    val secondField by viewModel.secondField.collectAsState()
    val result by viewModel.result.collectAsState()

    val snackbarHostState = LocalSnackbarHostState.current

    LaunchedEffect(uiState) {
        if (uiState is RangeScreenUiState.Error) {
            val toastText = (uiState as RangeScreenUiState.Error).errorMessage
            snackbarHostState.showMessage(message = toastText)
        }
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NumberInputField(
                value = firstField,
                onValueChange = { viewModel.updateFirstField(newValue = it) },
                placeholderText = stringResource(R.string.first_number)
            )
            ScreenSpacer()
            NumberInputField(
                value = secondField,
                onValueChange = { viewModel.updateSecondField(newValue = it) },
                placeholderText = stringResource(R.string.second_number)
            )
            ScreenSpacer()
            Text(
                text = stringResource(R.string.result),
                style = resultStyle
            )
            ScreenSpacer()
            UnderlineEmptyText(
                modifier = modifier.height(64.dp),
                text = result,
            )
        }

        GenerateButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp),
            onClick = remember {
                {
                    viewModel.generateRandomNumberInRange(
                        firstValue = firstField,
                        secondValue = secondField,
                    )
                }
            }
        )
        ErrorSnackbar(
            snackbarHostState = snackbarHostState,
            modifier = modifier.padding(horizontal = 16.dp)
        )
    }
}

@Composable
private fun ScreenSpacer() {
    Spacer(modifier = Modifier.height(16.dp))
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
    RandomizerTheme {
        RangeScreen(viewModel = koinViewModel())
    }
}
