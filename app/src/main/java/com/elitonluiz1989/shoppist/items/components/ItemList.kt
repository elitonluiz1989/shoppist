package com.elitonluiz1989.shoppist.items.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import com.elitonluiz1989.shoppist.items.ItemEvent
import com.elitonluiz1989.shoppist.items.ItemState
import kotlinx.coroutines.launch

@Composable
fun ItemsList(
    state: ItemState,
    onEvent: (ItemEvent) -> Unit,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val haptics = LocalHapticFeedback.current

    LazyColumn(
        state = listState,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
            .background(MaterialTheme.colorScheme.secondary)
            .padding(horizontal = 8.dp)
    ) {
        items(
            items = state.items,
            key = { item -> item.id },
        ) { item ->
            ItemRow(
                snackbarHostState = snackbarHostState,
                haptics = haptics,
                onEvent = onEvent,
                item = item
            )
        }
    }

    LaunchedEffect(state.items.size) {
        if (state.items.isEmpty()) return@LaunchedEffect

        coroutineScope.launch {
            listState.animateScrollToItem(state.items.lastIndex)
        }
    }
}