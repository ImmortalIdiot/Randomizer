package com.immortalidiot.randomizer.ui.list

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.immortalidiot.randomizer.R
import com.immortalidiot.randomizer.ui.components.button.GenerateButton
import com.immortalidiot.randomizer.ui.components.field.UnderlineEmptyText
import com.immortalidiot.randomizer.ui.components.snackbar.ErrorSnackbar
import com.immortalidiot.randomizer.ui.components.snackbar.LocalSnackbarHostState
import com.immortalidiot.randomizer.ui.components.snackbar.showMessage
import com.immortalidiot.randomizer.ui.theme.RandomizerTheme
import org.koin.androidx.compose.koinViewModel

// FIXME: the app crashes if there are more than 9 items
@Composable
fun ListScreen(
    viewModel: ListScreenViewModel
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val snackbarHostState = LocalSnackbarHostState.current

    val resultStyle = MaterialTheme.typography.headlineSmall

    val uiState by viewModel.uiState.collectAsState()

    val focusRequesters by viewModel.focusRequesters.collectAsState()

    val textFields by viewModel.textFields.collectAsState()
    val countTextFields = textFields.size

    val result by viewModel.result.collectAsState()

    LaunchedEffect(textFields.size) {
        if (textFields.size > 2) {
            focusRequesters.lastOrNull()?.requestFocus()
        }
    }

    LaunchedEffect(uiState) {
        if (uiState is ListScreenUiState.Error) {
            val snackbarText = (uiState as ListScreenUiState.Error).message
            snackbarHostState.showMessage(message = snackbarText)
        }
    }

    Box(
        modifier = Modifier
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
        Column(
            modifier = Modifier.padding(top = 48.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(count = countTextFields, key = { index -> index }) { index ->
                    val currentFocusRequester = focusRequesters.getOrNull(index) ?: FocusRequester()

                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 32.dp)
                            .focusRequester(currentFocusRequester),
                        value = textFields.getOrNull(index) ?: "",
                        onValueChange = { newValue ->
                            viewModel.updateTextField(index, newValue)
                        },
                        label = {
                            Text(text = stringResource(R.string.item) + " ${index + 1}")
                        },
                        maxLines = 1,
                        singleLine = true,
                        keyboardActions = KeyboardActions(onDone = {
                            val nextFocus = focusRequesters.getOrNull(index + 1)

                            if (nextFocus != null) {
                                nextFocus.requestFocus()
                            } else {
                                viewModel.addNewTextField()
                            }
                        })
                    )
                }
            }

            Text(
                text = stringResource(R.string.result),
                style = resultStyle
            )

            UnderlineEmptyText(
                modifier = Modifier.height(64.dp),
                text = result,
            )
        }

        GenerateButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp),
            onClick = remember { { viewModel.generateRandomElementFromList() } }
        )

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 32.dp, end = 16.dp)
                .size(48.dp),
            shape = CircleShape,
            onClick = remember { { viewModel.addNewTextField() } }
        ) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = ""
            )
        }

        ErrorSnackbar(
            snackbarHostState = snackbarHostState,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
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
