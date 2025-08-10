package com.elitonluiz1989.shoppist.items.components

import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.elitonluiz1989.shoppist.R
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ItemFormPicker(
    @StringRes label: Int,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTextStyle.current,
) {
    val items = remember { (1..99).map { it.toString() } }
    val interactionSource = remember { MutableInteractionSource() }

    BasicTextField(
        value = value,
        onValueChange = {},
        enabled = false,
        readOnly = true,
        interactionSource = interactionSource,
        textStyle = textStyle,
        decorationBox = {
            TextFieldDefaults.DecorationBox(
                value = value,
                visualTransformation = VisualTransformation.None,
                innerTextField = {
                    InnerTextField(
                        selectedItem = value,
                        values = items,
                        onValueChange = onValueChange,
                        textStyle = textStyle
                    )
                },
                enabled = true,
                singleLine = true,
                isError = false,
                interactionSource = interactionSource,
                label = {
                    Text(
                        text = stringResource(label),
                        color = MaterialTheme.colorScheme.secondary
                    )
                },
                container = {
                    TextFieldDefaults.Container(
                        enabled = true,
                        isError = false,
                        interactionSource = interactionSource,
                        shape = MaterialTheme.shapes.small,
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            focusedTextColor = MaterialTheme.colorScheme.onSurface,
                            cursorColor = MaterialTheme.colorScheme.secondary,
                            focusedLabelColor = MaterialTheme.colorScheme.secondary,
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
                        )
                    )
                }
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .focusable(interactionSource = interactionSource)
    )
}

@Preview(showBackground = true)
@Composable
fun ItemFormPickerPreview() {
    ItemFormPicker(
        label = R.string.items_screen_form_quantity,
        value = "1",
        onValueChange = {}
    )
}

@Composable
private fun pixelsToDp(pixels: Int): Dp = with(LocalDensity.current) { pixels.toDp() }

@Composable
private fun InnerTextField(
    selectedItem: String,
    values: List<String>,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle,
) {
    val listScrollMiddle = Int.MAX_VALUE / 2
    val listStartIndex = listScrollMiddle - listScrollMiddle % values.size
    val state = rememberLazyListState(initialFirstVisibleItemIndex = listStartIndex)
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = state)
    val itemHeightPx = remember { mutableIntStateOf(0) }
    val itemHeightDp = pixelsToDp(itemHeightPx.intValue)

    fun getItem(index: Int, items: List<String>) = items[index % items.size]

    LaunchedEffect(state) {
        snapshotFlow { state.firstVisibleItemIndex }
            .map { index -> getItem(index, values) }
            .distinctUntilChanged()
            .collect { selected -> onValueChange(selected) }
    }

    LaunchedEffect(selectedItem) {
        val index = values.indexOf(selectedItem)

        state.scrollToItem(index)
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.height(itemHeightDp)
    ) {
        LazyColumn(
            state = state,
            flingBehavior = flingBehavior,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.matchParentSize()
        ) {
            items(count = Int.MAX_VALUE) { index ->
                Text(
                    text = getItem(index, values),
                    style = textStyle.copy(color = MaterialTheme.colorScheme.primary),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.onSizeChanged { itemHeightPx.intValue = it.height }
                )
            }
        }
    }
}