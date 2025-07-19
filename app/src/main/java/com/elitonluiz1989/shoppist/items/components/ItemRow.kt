package com.elitonluiz1989.shoppist.items.components

import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.elitonluiz1989.domain.Item
import com.elitonluiz1989.shoppist.items.ItemEvent

@Composable
fun ItemRow(
    haptics: HapticFeedback,
    onEvent: (ItemEvent) -> Unit,
    item: Item,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Gray, MaterialTheme.shapes.small)
            .padding(16.dp)
            .combinedClickable(
                onClick = { },
                onLongClick = {
                    haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                    onEvent(ItemEvent.UpdateForm(item))
                },
                onDoubleClick = {
                    onEvent(ItemEvent.UpdateForm(item))
                }
            )

    ) {
        ItemRowText(
            item.name,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(3f)
        )

        ItemRowText(
            item.total.toString(),
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f)
        )
    }
}
