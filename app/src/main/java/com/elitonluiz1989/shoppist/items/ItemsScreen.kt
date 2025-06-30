package com.elitonluiz1989.shoppist.items

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.elitonluiz1989.domain.Item
import com.elitonluiz1989.shoppist.items.components.ErrorMessage
import com.elitonluiz1989.shoppist.items.components.ItemForm
import com.elitonluiz1989.shoppist.items.components.ItemsList
import com.elitonluiz1989.shoppist.items.components.Loading

@Composable
fun ItemsScreen(paddingValues: PaddingValues) {
    val viewModel: ItemViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()

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
    ) {

        if (state.isLoading) {
            Loading()

            return
        }

        ItemForm(state, onEvent)

        Spacer(modifier = Modifier.height(16.dp))

        ErrorMessage(state)

        ItemsList(state, onEvent)
    }
}
