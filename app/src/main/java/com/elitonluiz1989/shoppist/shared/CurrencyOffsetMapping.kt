package com.elitonluiz1989.shoppist.shared

import android.icu.text.NumberFormat
import androidx.compose.ui.text.input.OffsetMapping
import java.util.Locale

class CurrencyOffsetMapping(
    private val originalText: String,
    private val formattedText: String,
    private val currentLocale: Locale
) : OffsetMapping {
    private val currencySymbol: String by lazy {
        NumberFormat.getCurrencyInstance(currentLocale).currency?.symbol ?: ""
    }

    override fun originalToTransformed(offset: Int): Int {
        if (originalText.isEmpty()) return 0

        if (offset == 0 && formattedText.startsWith(currencySymbol)) return currencySymbol.length

        var originalDigitsCount = 0

        for (i in 0 until offset) {
            if (originalText.getOrNull(i)?.isDigit() == false) continue

            originalDigitsCount++
        }

        var transformedOffset = 0
        var transformedDigitsCount = 0
        var prefixOffset = 0

        if (formattedText.startsWith(currencySymbol)) {
            prefixOffset = currencySymbol.length
        }

        if (originalDigitsCount == 0) {
            if (offset > 0 && originalText.substring(0, offset).all { !it.isDigit() }) {
                val nonCurrencyCharCount = originalText
                    .take(offset)
                    .count { !it.isDigit() && it != '.' && it != ',' }

                return prefixOffset + nonCurrencyCharCount;
            }

            return prefixOffset
        }


        for (i in prefixOffset until formattedText.length) {
            if (formattedText[i].isDigit()) {
                transformedDigitsCount++
            }

            if (transformedDigitsCount == originalDigitsCount) {
                transformedOffset = i + 1
                break
            }

            if (i == formattedText.length - 1) {
                transformedOffset = formattedText.length
                break
            }
        }

        return transformedOffset.coerceIn(0, formattedText.length)
    }

    override fun transformedToOriginal(offset: Int): Int {
        if (formattedText.isEmpty()) return 0

        if (originalText.isEmpty() && offset > 0) return 0

        var originalOffset = 0
        var prefixOffset = 0

        if (formattedText.startsWith(currencySymbol)) {
            prefixOffset = currencySymbol.length
        }

        if (offset <= prefixOffset) return 0

        var relevantTransformedLength = 0

        for(i in prefixOffset until offset) {
            if (formattedText.getOrNull(i)?.isDigit() == true) {
                relevantTransformedLength++
            }
        }

        var originalDigitsCount = 0

        for (i in originalText.indices) {
            if (originalText[i].isDigit()) {
                originalDigitsCount++
                originalOffset = i + 1

                if (originalDigitsCount == relevantTransformedLength) {
                    break
                }
            } else if (originalDigitsCount == 0 && i + 1 >= offset - prefixOffset) {
                originalOffset = i + 1
                break
            }

            if(i == originalText.length -1 && originalDigitsCount < relevantTransformedLength){
                originalOffset = originalText.length
            }
        }

        return originalOffset.coerceIn(0, originalText.length)
    }
}