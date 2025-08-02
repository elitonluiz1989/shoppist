package com.elitonluiz1989.shoppist.items

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsControllerCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.elitonluiz1989.domain.Item
import com.elitonluiz1989.shoppist.items.components.ItemForm
import com.elitonluiz1989.shoppist.items.components.ItemsList
import com.elitonluiz1989.shoppist.items.components.ItemsTotalBar
import com.elitonluiz1989.shoppist.shared.components.Loading

@Composable
fun ItemsScreen(
    snackbarHostState: SnackbarHostState,
    paddingValues: PaddingValues,
) {
    val viewModel: ItemViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    ItemContent(
        state,
        viewModel::onEvent,
        snackbarHostState,
        paddingValues
    )

    if (!LocalView.current.isInEditMode) {
        val view = LocalView.current

        SideEffect {
            val window = (view.context as android.app.Activity).window
            val insetsController = WindowInsetsControllerCompat(window, view)

            insetsController.isAppearanceLightStatusBars = false
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemsScreenPreview() {
    val state = ItemState(
        items = listOf(
            Item(id = 1, name = "Item 1", quantity = 1, price = 2575),
            Item(id = 2, name = "Item 2", quantity = 1, price = 1000)
        )
    )

    ItemContent(
        state = state,
        onEvent = {},
        snackbarHostState = SnackbarHostState(),
        paddingValues = PaddingValues(16.dp)
    )
}

@Composable
private fun ItemContent(
    state: ItemState,
    onEvent: (ItemEvent) -> Unit,
    snackbarHostState: SnackbarHostState,
    paddingValues: PaddingValues,
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
                text = stringResource(id = state.error),
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
                snackbarHostState = snackbarHostState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
        }

        ItemsTotalBar(state = state)
    }
}

