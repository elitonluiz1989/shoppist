package com.elitonluiz1989.shoppist.items.components

import com.elitonluiz1989.shoppist.shared.CurrencyVisualTransformation
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elitonluiz1989.shoppist.R
import com.elitonluiz1989.shoppist.items.ItemEvent
import com.elitonluiz1989.shoppist.items.ItemState

@Composable
fun ItemForm(
    state: ItemState,
    onEvent: (ItemEvent) -> Unit
) {
    Column (
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 6.dp)
            .fillMaxWidth()
    ) {
        ItemFormField(
            value = state.form.name,
            onValueChange = { onEvent(ItemEvent.UpdateName(it)) },
            onFocusChanged = { onEvent(ItemEvent.MarkFormAsTouched) },
            label = R.string.items_screen_form_name,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            isError = state.form.nameInvalid,
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            ItemFormField(
                value = state.form.quantity,
                onValueChange = { onEvent(ItemEvent.UpdateQuantity(it)) },
                onFocusChanged = { onEvent(ItemEvent.MarkFormAsTouched) },
                label = R.string.items_screen_form_quantity,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                textStyle = TextStyle(textAlign = TextAlign.End),
                isError = state.form.quantityInvalid,
                modifier = Modifier
                    .weight(1f)
            )

            ItemFormField(
                value = state.form.price,
                onValueChange = { onEvent(ItemEvent.UpdatePrice(it)) },
                onFocusChanged = { onEvent(ItemEvent.MarkFormAsTouched) },
                label = R.string.items_screen_form_price,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                textStyle = TextStyle(textAlign = TextAlign.End),
                isError = state.form.priceInvalid,
                visualTransformation = CurrencyVisualTransformation(),
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .weight(3f)
            )

            ItemFormButton(state, onEvent)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemFromPreview() {
    val state = ItemState()

    ItemForm(
        state = state,
        onEvent = {}
    )
}