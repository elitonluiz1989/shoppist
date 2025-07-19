package com.elitonluiz1989.shoppist.items.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.elitonluiz1989.shoppist.R
import com.elitonluiz1989.shoppist.items.ItemState
import com.elitonluiz1989.shoppist.items.shared.TextResource

@Composable
fun ItemsTotalBar(
    state: ItemState
) {
    val total = state.items.sumOf { it.total }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 6.dp)
    ) {
        TextResource(
            id = R.string.items_screen_total,
            textAlign = TextAlign.Companion.End,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.weight(3f)
        )

        Text(
            text = total.toString(),
            textAlign = TextAlign.Companion.End,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.weight(1f)
        )
    }
}