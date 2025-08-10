package com.elitonluiz1989.shoppist.items.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elitonluiz1989.shoppist.R
import com.elitonluiz1989.shoppist.items.ItemEvent
import com.elitonluiz1989.shoppist.items.ItemState
import com.elitonluiz1989.shoppist.shared.CurrencyVisualTransformation

@Composable
fun ItemForm(
    state: ItemState,
    onEvent: (ItemEvent) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    fun closeKeyboard() {
        keyboardController?.hide()
        focusManager.clearFocus()
    }

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
            Box(
                modifier = Modifier.weight(0.3f)
                    .clickable(onClick = {
                        closeKeyboard()
                    })
            ) {
                ItemFormPicker(
                    label = R.string.items_screen_form_quantity,
                    value = state.form.quantity,
                    onValueChange = { onEvent(ItemEvent.UpdateQuantity(it)) },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            ItemFormField(
                value = state.form.price,
                onValueChange = { onEvent(ItemEvent.UpdatePrice(it)) },
                onFocusChanged = { onEvent(ItemEvent.MarkFormAsTouched) },
                label = R.string.items_screen_form_price,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        onEvent(ItemEvent.Submit)
                        closeKeyboard()
                    }
                ),
                textStyle = TextStyle(textAlign = TextAlign.End),
                isError = state.form.priceInvalid,
                visualTransformation = CurrencyVisualTransformation(),
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .weight(0.7f)
            )

            ItemFormButton(state, onEvent, closeKeyboard = { closeKeyboard() })
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