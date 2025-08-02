package com.elitonluiz1989.shoppist.items.components

import ItemRowCurrencyText
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.elitonluiz1989.domain.Item
import com.elitonluiz1989.shoppist.R
import com.elitonluiz1989.shoppist.items.ItemEvent

@Composable
fun ItemRow(
    snackbarHostState: SnackbarHostState,
    haptics: HapticFeedback,
    onEvent: (ItemEvent) -> Unit,
    item: Item,
) {
    val snackbarMessage = stringResource(R.string.items_screen_item_removed)
    val snackbarActionLabel = stringResource(R.string.items_screen_item_removed_undo)
    var itemPendingToRemove by remember { mutableStateOf<Item?>(null) }
    var itemIsVisible by remember(item.id) { mutableStateOf(true) }

    val dismissBoxState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            if (it == SwipeToDismissBoxValue.EndToStart) {
                if (itemIsVisible) {
                    itemPendingToRemove = item
                    itemIsVisible = false
                }
            }

            false
        }
    )

    LaunchedEffect(itemPendingToRemove) {
        itemPendingToRemove?.let { currentItemToRemove ->
            val result = snackbarHostState.showSnackbar(
                message = snackbarMessage,
                actionLabel = snackbarActionLabel,
                duration = SnackbarDuration.Short
            )

            if (result == SnackbarResult.ActionPerformed) {
                itemIsVisible = true
            } else {
                onEvent(ItemEvent.Delete(currentItemToRemove))
            }

            itemPendingToRemove = null
        }
    }

    LaunchedEffect(itemIsVisible, dismissBoxState.currentValue) {
        if (itemIsVisible && dismissBoxState.currentValue != SwipeToDismissBoxValue.Settled) {
            dismissBoxState.reset()
        }
    }

    Column {
        if (itemIsVisible) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
            )
        }

        AnimatedVisibility(
            visible = itemIsVisible,
            exit = shrinkVertically() + fadeOut()
        ) {
            SwipeToDismissBox(
                state = dismissBoxState,
                backgroundContent = {
                    ItemRowBackground(dismissBoxState)
                },
                content = {
                    ItemRowContent(item, haptics, onEvent)
                }
            )
        }
    }
}

@Composable
private fun ItemRowBackground(dismissBoxState: SwipeToDismissBoxState) {
    val color = when (dismissBoxState.dismissDirection) {
        SwipeToDismissBoxValue.EndToStart -> Color.Red
        else -> Color.Transparent
    }

    Box(
        contentAlignment = Alignment.CenterEnd,
        modifier = Modifier
            .fillMaxWidth()
            .background(color, RoundedCornerShape(8.dp))
            .padding(16.dp),
    ) {
        Icon(
            imageVector = Default.Delete,
            contentDescription = "Remover",
            tint = Color.White
        )
    }
}

@Composable
private fun ItemRowContent(
    item: Item,
    haptics: HapticFeedback,
    onEvent: (ItemEvent) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondary, RoundedCornerShape(8.dp))
            .border(1.dp, MaterialTheme.colorScheme.tertiary, MaterialTheme.shapes.small)
            .padding(16.dp)
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
        ItemRowText(
            text = item.name,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(3f)
        )

        ItemRowCurrencyText(
            text = item.total.toString(),
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f)
        )
    }
}
