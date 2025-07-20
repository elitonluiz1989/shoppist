package com.elitonluiz1989.shoppist.items

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.elitonluiz1989.domain.Item
import com.elitonluiz1989.shoppist.items.components.ItemForm
import com.elitonluiz1989.shoppist.items.components.ItemsList
import com.elitonluiz1989.shoppist.items.components.ItemsTotalBar
import com.elitonluiz1989.shoppist.items.shared.Loading

@Composable
fun ItemsScreen(paddingValues: PaddingValues) {
    val viewModel: ItemViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    ItemContent(state, viewModel::onEvent, paddingValues)
}

@Preview(showBackground = true)
@Composable
fun ItemsScreenPreview() {
    val state = ItemState(
        items = listOf(
            Item(id = 1, name = "Item 1", quantity = 1, price = 25.75.toBigDecimal()),
            Item(id = 2, name = "Item 2", quantity = 1, price = 10.00.toBigDecimal())
        )
    )

    ItemContent(
        state = state,
        onEvent = {},
        paddingValues = PaddingValues(16.dp)
    )
}

@Composable
private fun ItemContent(
    state: ItemState,
    onEvent: (ItemEvent) -> Unit,
    paddingValues: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 2.dp)
    ) {
        ItemForm(
            state = state,
            onEvent = onEvent
        )

        if (state.error != null) {
            Text(
                text = state.error,
                textAlign = TextAlign.Center,
                color = Color.Red,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp, horizontal = 8.dp)
            )
        }

        if (state.isLoading) {
            Loading()
        } else {
            ItemsList(
                state = state,
                onEvent = onEvent,
                modifier = Modifier.fillMaxWidth().weight(1f)
            )
        }

        ItemsTotalBar(state = state)
    }
}

