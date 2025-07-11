package com.elitonluiz1989.shoppist.items.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.elitonluiz1989.shoppist.R
import com.elitonluiz1989.shoppist.items.ItemState

@Composable
fun ItemsTotalBar(state: ItemState) {
    val total = state.items.sumOf { it.total }

    Row(
        modifier = Modifier.Companion.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.item_screen_total),
            textAlign = TextAlign.Companion.End,
            modifier = Modifier.Companion.weight(3f)
        )

        Text(
            text = total.toString(),
            textAlign = TextAlign.Companion.End,
            modifier = Modifier.Companion.weight(1f)
        )
    }
}