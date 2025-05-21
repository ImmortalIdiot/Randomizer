package com.immortalidiot.randomizer.ui.history

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.immortalidiot.randomizer.R
import com.immortalidiot.randomizer.ui.components.button.ScrollToTopButton
import com.immortalidiot.randomizer.ui.components.items.HistoryItem

@Composable
fun HistoryScreen(
    viewModel: HistoryScreenViewModel,
    modifier: Modifier = Modifier
) {
    val historyList = viewModel.historyList.collectAsState()
    val listState = rememberLazyListState()

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
                items(historyList.value) { history ->
                    HistoryItem(history = history)
                }
            }

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
