package com.immortalidiot.randomizer.ui.history

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.immortalidiot.randomizer.R
import com.immortalidiot.randomizer.ui.components.animations.CircularIndicator
import com.immortalidiot.randomizer.ui.components.button.ScrollToTopButton
import com.immortalidiot.randomizer.ui.components.items.HistoryItem

@Composable
fun HistoryScreen(
    viewModel: HistoryScreenViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val historyList = viewModel.historyList.collectAsState()
    val listState = rememberLazyListState()

    val duration = 1500

    val selectedOne = viewModel.selectedOne.collectAsState()

    AnimatedContent(
        targetState = uiState,
        transitionSpec = {
            fadeIn(
                animationSpec = tween(duration)
            ) togetherWith fadeOut(animationSpec = tween(duration))
        }
    ) { targetState ->
        when (targetState) {
            is HistoryScreenUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularIndicator()
                }
            }

            else -> {
                if (historyList.value.isEmpty()) {
                    Box(
                        modifier = modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No history yet",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                } else {
                    Box {
                        LazyColumn(
                            state = listState,
                            modifier = modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(items = historyList.value, key = { it.id }) { history ->
                                var selected by rememberSaveable { mutableStateOf(false) }

                                HistoryItem(
                                    modifier = Modifier.combinedClickable(
                                        onClick = {
                                            selected = !selected
                                            if (selected) {
                                                viewModel.addItemToList(history)
                                            } else {
                                                viewModel.removeItemFromList(history)
                                            }
                                        },
                                        onLongClick = {
                                            viewModel.toggleSelectedOne()
                                            selected = !selected
                                            if (selected) {
                                                viewModel.addItemToList(history)
                                            } else {
                                                viewModel.removeItemFromList(history)
                                            }
                                        }
                                    ),
                                    history = history,
                                    isSelected = selected,
                                    selectedOne = selectedOne.value,
                                    onCheckedChange = {
                                        selected = !selected
                                        if (selected) {
                                            viewModel.addItemToList(history)
                                        } else {
                                            viewModel.removeItemFromList(history)
                                        }
                                    }
                                )
                            }
                        }
                        if (!selectedOne.value) {
                            Button(
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .padding(bottom = 16.dp)
                                    .height(48.dp),
                                onClick = remember { { viewModel.deleteHistory() } },
                            ) {
                                Text(
                                    text = stringResource(R.string.clear_history),
                                    textAlign = TextAlign.Center
                                )
                            }
                        } else {
                            Button(
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .padding(bottom = 16.dp)
                                    .height(48.dp),
                                onClick = remember { { viewModel.deleteHistoryByList() } },
                            ) {
                                Text(
                                    text = stringResource(R.string.clear_selected_history),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                        ScrollToTopButton(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(bottom = 16.dp, end = 32.dp)
                                .size(48.dp),
                            listState = listState,
                        )
                    }
                }
            }
        }
    }
}
