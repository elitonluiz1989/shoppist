package com.elitonluiz1989.shoppist.items.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elitonluiz1989.domain.Item
import com.elitonluiz1989.shoppist.R
import com.elitonluiz1989.shoppist.items.ItemEvent
import com.elitonluiz1989.shoppist.items.ItemState

@Composable
fun ItemFormButton(
    state: ItemState,
    onEvent: (ItemEvent) -> Unit,
) {
    val icon = handleIcon(state)
    val contentDescription = handleContentDescription(state)

    Button(
        shape = RoundedCornerShape(size = 5.dp),
        onClick = {
            addEvent(state, onEvent)
        },
        modifier = Modifier.height(56.dp)

    ) {
        Icon(
            imageVector = icon,
            contentDescription = stringResource(contentDescription),
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ItemFormButtonPreview() {
    val state = ItemState()

    ItemFormButton(
        state = state,
        onEvent = {}
    )
}

@Composable
private fun handleIcon(state: ItemState): ImageVector {
    return if (state.id == 0.toLong()) {
        Icons.Filled.Add
    } else {
        Icons.Filled.Done
    }
}

@Composable
private fun handleContentDescription(state: ItemState): Int {
    return if (state.id == 0.toLong()) {
        R.string.add
    } else {
        R.string.update
    }
}

private fun addEvent(
    state: ItemState,
    onEvent: (ItemEvent) -> Unit
) {
    val item = Item(
        id = state.id,
        name = state.name,
        quantity = state.quantity.toShort(),
        price = state.priceBigDecimal
    )

    if (!item.validate()) return

    onEvent(
        ItemEvent.Add(item)
    )
}