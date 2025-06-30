package com.elitonluiz1989.domain

import java.math.BigDecimal

data class Item(
    val id: Long,
    val name: String,
    val quantity: Short,
    val price: BigDecimal
) {
    fun validate(): Boolean {
        return name.isNotBlank() && quantity > 0 && price > BigDecimal.ZERO;
    }
}