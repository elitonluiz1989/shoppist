package com.elitonluiz1989.shoppist.items.components

import androidx.annotation.StringRes
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elitonluiz1989.shoppist.R
import com.elitonluiz1989.shoppist.shared.components.TextResource

@Composable
fun ItemFormField(
    value: String,
    onValueChange: (String) -> Unit,
    onFocusChanged: () -> Unit,
    @StringRes label: Int,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    textStyle: TextStyle = TextStyle(),
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { TextResource(label) },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        shape = RoundedCornerShape(8.dp),
        textStyle = textStyle.copy(color = MaterialTheme.colorScheme.primary),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            cursorColor = MaterialTheme.colorScheme.secondary,
            focusedLabelColor = MaterialTheme.colorScheme.secondary,
            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
        ),
        isError = isError,
        visualTransformation = visualTransformation,
        modifier = modifier
            .onFocusChanged {
                if (it.isFocused) {
                    onFocusChanged()
                }
            }
    )
}

@Preview(showBackground = true)
@Composable
fun ItemFormFieldPreview() {
    ItemFormField(
        value = "Item 001",
        onValueChange = {},
        onFocusChanged = {},
        label = R.string.items_screen_form_name,
        keyboardOptions = KeyboardOptions.Default,
    )
}