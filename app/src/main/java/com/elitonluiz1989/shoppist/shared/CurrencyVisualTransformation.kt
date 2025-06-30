package com.elitonluiz1989.shoppist.shared

import android.icu.text.DecimalFormatSymbols
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.core.net.ParseException
import java.text.NumberFormat
import java.util.Locale

class CurrencyVisualTransformation : VisualTransformation {
    private val currentLocale = Locale.getDefault()
    private val currencyFormatter = NumberFormat.getCurrencyInstance(currentLocale)
    private val numberFormat = NumberFormat.getNumberInstance(currentLocale)
    private val decimalFormatSymbols = DecimalFormatSymbols.getInstance(currentLocale)

    override fun filter(text: AnnotatedString): TransformedText {
        var sanitizedInput = sanitizeInputValue(text.text)
        val numericValue = parseInputValue(sanitizedInput)
        val formattedCurrency = currencyFormatter.format(numericValue)

        return TransformedText(
            text = AnnotatedString(formattedCurrency),
            offsetMapping = CurrencyOffsetMapping(
                originalText = text.text,
                formattedText = formattedCurrency,
                currentLocale = currentLocale
            )
        )
    }

    private fun sanitizeInputValue(value: String): String {
        var sanitizedValue = value.replace(decimalFormatSymbols.groupingSeparator.toString(), "")

        if (decimalFormatSymbols.decimalSeparator == '.') return sanitizedValue

        return sanitizedValue.replace(decimalFormatSymbols.decimalSeparator, '.')
    }

    private fun parseInputValue(value: String): Double {
        if (value.isEmpty()) return 0.0

        return try {
            numberFormat.parse(value)?.toDouble() ?: 0.0
        } catch (e: ParseException) {
            0.0
        }
    }
}
