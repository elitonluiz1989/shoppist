package com.elitonluiz1989.shoppist.items.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.elitonluiz1989.shoppist.R
import com.elitonluiz1989.shoppist.items.ItemState
import com.elitonluiz1989.shoppist.shared.components.TextResource
import com.elitonluiz1989.shoppist.shared.currencyFormatter
import com.elitonluiz1989.shoppist.shared.getCurrentLocale

@Composable
fun ItemsTotalBar(
    state: ItemState
) {
    val total = state.items.sumOf { it.total }
    val locale = getCurrentLocale()
    val totalFormatted = currencyFormatter(total.toString(), locale)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
            )
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background, RoundedCornerShape(8.dp))
                .padding(16.dp)
        ) {
            TextResource(
                id = R.string.items_screen_total,
                textAlign = TextAlign.Companion.End,
                color = Color.White,
                modifier = Modifier.weight(3f)
            )

            Text(
                text = totalFormatted,
                textAlign = TextAlign.Companion.End,
                color = Color.White,
                modifier = Modifier.weight(1f)
            )
        }
    }

    Spacer(modifier = Modifier.height(8.dp))
}