package com.elitonluiz1989.shoppist.items.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elitonluiz1989.domain.Item
import com.elitonluiz1989.shoppist.R
import com.elitonluiz1989.shoppist.items.ItemEvent
import com.elitonluiz1989.shoppist.items.ItemState

@Composable
fun ItemForm(
    state: ItemState,
    onEvent: (ItemEvent) -> Unit,
) {
    Column {
        OutlinedTextField(
            value = state.name,
            onValueChange = { onEvent(ItemEvent.UpdateName(it)) },
            label = { Text(stringResource(R.string.item_screen_form_name)) },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            textStyle = TextStyle(fontSize = 14.sp),
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = state.quantity,
                onValueChange = { onEvent(ItemEvent.UpdateQuantity(it)) },
                label = { Text(stringResource(R.string.item_screen_form_quantity)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                singleLine = true,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            )

            OutlinedTextField(
                value = state.price,
                onValueChange = { onEvent(ItemEvent.UpdatePrice(it)) },
                label = { Text(stringResource(R.string.item_screen_form_price)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                textStyle = TextStyle(textAlign = TextAlign.End),
                modifier = Modifier.weight(3f)
            )

            Button(
                shape = RoundedCornerShape(size = 5.dp),
                onClick = {
                    addEvent(state, onEvent)
                },
                modifier = Modifier
                    .height(64.dp)
                    .padding(top = 8.dp, start = 8.dp)

            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(R.string.add)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemFromPreview() {
    val state = ItemState()

    ItemForm(state = state, onEvent = {})
}

private fun addEvent(state: ItemState, onEvent: (ItemEvent) -> Unit) {
    val item = Item(
        id = 0,
        name = state.name,
        quantity = state.quantity.toShort(),
        price = state.price.toBigDecimal()
    )

    if (!item.validate()) return

    onEvent(
        ItemEvent.Add(item)
    )
}