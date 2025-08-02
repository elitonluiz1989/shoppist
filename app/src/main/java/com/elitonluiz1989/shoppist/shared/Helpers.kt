package com.elitonluiz1989.shoppist.shared

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.core.os.ConfigurationCompat
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

@Composable
fun getCurrentLocale(): Locale {
    val configuration = LocalConfiguration.current
    val locales = ConfigurationCompat.getLocales(configuration)

    return locales[0] ?: Locale.getDefault()
}

fun currencyFormatter(text: String, locale: Locale): String {
    val onlyDigits = text.filter { it.isDigit() }
    val amount = onlyDigits.toBigDecimalOrNull()?.divide(100.toBigDecimal()) ?: BigDecimal.ZERO
    val formatted = NumberFormat.getCurrencyInstance(locale).format(amount)

    return formatted
}
