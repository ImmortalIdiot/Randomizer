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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.immortalidiot.randomizer.R
import com.immortalidiot.randomizer.ui.components.animations.CircularIndicator
import com.immortalidiot.randomizer.ui.components.buttons.ButtonWithTextRes
import com.immortalidiot.randomizer.ui.components.buttons.ScrollToTopButton
import com.immortalidiot.randomizer.ui.components.items.HistoryItem

@Composable
fun HistoryScreen(
    viewModel: HistoryScreenViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val historyList by viewModel.historyList.collectAsState()
    val selectedIds by viewModel.selectedIds.collectAsState()
    val listState = rememberLazyListState()

    val contentLoadingAnimation = 1500

    AnimatedContent(
        targetState = uiState,
        transitionSpec = {
            fadeIn(animationSpec = tween(contentLoadingAnimation)) togetherWith
                    fadeOut(animationSpec = tween(contentLoadingAnimation))
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
                if (historyList.isEmpty()) {
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
                            items(
                                items = historyList,
                                key = { it.id }
                            ) { history ->
                                val isSelected = history.id in selectedIds

                                HistoryItem(
                                    modifier = Modifier.combinedClickable(
                                        onClick = {
                                            viewModel.toggleSelection(history.id)
                                        },
                                        onLongClick = {
                                            viewModel.toggleSelection(history.id)
                                        }
                                    ),
                                    history = history,
                                    isSelected = isSelected,
                                    selectedOne = selectedIds.isNotEmpty(),
                                    onCheckedChange = {
                                        viewModel.toggleSelection(history.id)
                                    }
                                )
                            }
                        }

                        ButtonWithTextRes(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 16.dp)
                                .height(48.dp),
                            onClick = {
                                if (selectedIds.isEmpty()) {
                                    viewModel.deleteHistory()
                                } else {
                                    viewModel.deleteSelectedHistory()
                                }
                            },
                            textRes = if (selectedIds.isEmpty()) {
                                R.string.clear_history
                            } else {
                                R.string.clear_selected_history
                            }
                        )

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
