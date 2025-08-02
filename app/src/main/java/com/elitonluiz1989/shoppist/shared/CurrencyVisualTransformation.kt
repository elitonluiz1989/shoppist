package com.elitonluiz1989.shoppist.shared

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import java.util.Locale

class CurrencyVisualTransformation(
    private val locale: Locale = Locale.getDefault()
) : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val formatted = currencyFormatter(text.text, locale)

        return TransformedText(
            androidx.compose.ui.text.AnnotatedString(formatted),

            object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    return formatted.length
                }

                override fun transformedToOriginal(offset: Int): Int {
                    return text.text.length
                }
            }
        )
    }
}