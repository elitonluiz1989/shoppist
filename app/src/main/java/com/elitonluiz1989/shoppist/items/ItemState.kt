package com.elitonluiz1989.shoppist.items

import com.elitonluiz1989.domain.Item
import java.math.BigDecimal

data class ItemState(
    val items: List<Item> = emptyList(),
    val id: Long = 0,
    val name: String = "Item",
    val quantity: String = "1",
    val price: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
) {
    val priceBigDecimal: BigDecimal
        get() = price.toBigDecimalOrNull() ?: BigDecimal.ZERO
}