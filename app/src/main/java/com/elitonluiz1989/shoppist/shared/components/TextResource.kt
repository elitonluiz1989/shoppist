package com.elitonluiz1989.shoppist.shared.components

import androidx.annotation.StringRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign


@Composable
fun TextResource(
    @StringRes id: Int,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = MaterialTheme.colorScheme.secondary,
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(id),
        textAlign = textAlign,
        color = color,
        modifier = modifier
    )
}