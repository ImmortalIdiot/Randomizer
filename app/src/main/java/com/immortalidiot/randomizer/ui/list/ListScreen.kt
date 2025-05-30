package com.immortalidiot.randomizer.ui.list

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.immortalidiot.randomizer.R
import com.immortalidiot.randomizer.ui.components.buttons.GenerateButton
import com.immortalidiot.randomizer.ui.components.fields.UnderlineEmptyText
import com.immortalidiot.randomizer.ui.components.snackbars.CustomSnackbar
import com.immortalidiot.randomizer.ui.components.snackbars.showMessage
import com.immortalidiot.randomizer.ui.providers.LocalSnackbarHostState
import com.immortalidiot.randomizer.ui.theme.RandomizerTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun ListScreen(
    viewModel: ListScreenViewModel,
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val snackbarHostState = LocalSnackbarHostState.current

    val resultStyle = MaterialTheme.typography.headlineSmall

    val uiState by viewModel.uiState.collectAsState()

    val textFields = viewModel.textFields
    val countTextFields = textFields.size

    val result by viewModel.result.collectAsState()

    val listState = rememberLazyListState()

    LaunchedEffect(uiState) {
        if (uiState is ListScreenUiState.Error) {
            val snackbarText = (uiState as ListScreenUiState.Error).message
            snackbarHostState.showMessage(message = snackbarText)
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures {
                    hideKeyboard(
                        keyboardController = keyboardController,
                        focusManager = focusManager
                    )
                }
            },
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(
            state = listState,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(bottom = 96.dp)
        ) {
            item {
                CustomSnackbar(
                    snackbarHostState = snackbarHostState,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = Color.Red
                )
            }

            items(count = 1) {
                Text(
                    text = stringResource(R.string.result),
                    style = resultStyle
                )
                UnderlineEmptyText(
                    modifier = Modifier.height(64.dp),
                    text = result,
                )
            }

            items(count = countTextFields, key = { index -> index }) { index ->
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    value = textFields.getOrNull(index) ?: "",
                    onValueChange = { newValue ->
                        viewModel.updateTextField(index, newValue)
                    },
                    label = {
                        Text(text = stringResource(R.string.item) + " ${index + 1}")
                    },
                    maxLines = 1,
                    singleLine = true
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
                .align(Alignment.BottomCenter)
        ) {
            GenerateButton(
                modifier = Modifier.align(Alignment.Center),
                onClick = { viewModel.generateRandomElementFromList() }
            )

            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 32.dp)
                    .size(48.dp),
                shape = CircleShape,
                onClick = { viewModel.addNewTextField() }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Add item"
                )
            }
        }
    }
}

private fun hideKeyboard(
    keyboardController: SoftwareKeyboardController?,
    focusManager: FocusManager
) {
    keyboardController?.hide()
    focusManager.clearFocus()
}

@Preview
@Composable
private fun ListScreenPreview() {
    RandomizerTheme {
        ListScreen(viewModel = koinViewModel())
    }
}
