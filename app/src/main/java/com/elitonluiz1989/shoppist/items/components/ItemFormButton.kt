package com.elitonluiz1989.shoppist.items.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elitonluiz1989.shoppist.R
import com.elitonluiz1989.shoppist.items.ItemEvent
import com.elitonluiz1989.shoppist.items.ItemState
import com.elitonluiz1989.shoppist.ui.theme.background

@Composable
fun ItemFormButton(
    state: ItemState,
    onEvent: (ItemEvent) -> Unit,
) {
    val icon = handleIcon(state)
    val contentDescription = handleContentDescription(state)

    Button(
        shape = RoundedCornerShape(5.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = background
        ),
        onClick = {
            onEvent(ItemEvent.Submit)
        },
        modifier = Modifier.height(56.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = stringResource(contentDescription)
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
    return if (state.form.idValid) Icons.Filled.Done
        else Icons.Filled.Add
}

@Composable
private fun handleContentDescription(state: ItemState): Int {
    return if (state.form.idValid) R.string.update
        else R.string.add
}