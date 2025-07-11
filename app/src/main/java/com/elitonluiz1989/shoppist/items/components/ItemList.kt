package com.elitonluiz1989.shoppist.items.components

import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.elitonluiz1989.shoppist.items.ItemEvent
import com.elitonluiz1989.shoppist.items.ItemState
import kotlinx.coroutines.launch

@Composable
fun ItemsList(
    state: ItemState,
    onEvent: (ItemEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val haptics = LocalHapticFeedback.current

    LazyColumn(
        state = listState,
        modifier = modifier.fillMaxWidth()
    ) {
        itemsIndexed(state.items) { index, item ->
            if (index > 0) {
                Spacer(
                    modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                )
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Gray, MaterialTheme.shapes.small)
                    .padding(10.dp)
                    .combinedClickable(
                        onClick = { },
                        onLongClick = {
                            haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                            onEvent(ItemEvent.UpdateForm(item))
                        },
                        onDoubleClick = {
                            onEvent(ItemEvent.UpdateForm(item))
                        }
                    )

            ) {
                TextColumn(
                    item.name,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(3f)
                )

                TextColumn(
                    item.total.toString(),
                    textAlign = TextAlign.End,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }

    LaunchedEffect(state.items.size) {
        if (state.items.isNotEmpty()) {
            coroutineScope.launch {
                listState.animateScrollToItem(state.items.lastIndex)
            }
        }
    }
}

@Composable
private fun TextColumn(
    text: String,
    textAlign: TextAlign = TextAlign.Start,
    maxLines: Int = 1,
    overflow: TextOverflow = TextOverflow.Visible,
    modifier: Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        textAlign = textAlign,
        maxLines = maxLines,
        overflow = overflow,
        modifier = modifier
    )
}