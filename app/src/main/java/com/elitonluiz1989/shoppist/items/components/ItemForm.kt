package com.elitonluiz1989.shoppist.items.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elitonluiz1989.domain.Item
import com.elitonluiz1989.shoppist.R
import com.elitonluiz1989.shoppist.items.ItemEvent
import com.elitonluiz1989.shoppist.items.ItemState
import com.elitonluiz1989.shoppist.shared.CurrencyVisualTransformation

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
            modifier = Modifier.fillMaxWidth()
        )

        Row {
            OutlinedTextField(
                value = state.quantity,
                onValueChange = { onEvent(ItemEvent.UpdateQuantity(it)) },
                label = { Text(stringResource(R.string.item_screen_form_quantity)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier
                    .weight(0.3f)
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
                visualTransformation = CurrencyVisualTransformation(),
                modifier = Modifier.weight(0.7f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                addEvent(state, onEvent)
            }
        ) {
            Text(stringResource(R.string.add))
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
