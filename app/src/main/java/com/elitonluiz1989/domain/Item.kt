package com.elitonluiz1989.domain

data class Item(
    val id: Long,
    val name: String,
    val quantity: Short,
    val price: Int
) {
    val total: Int
        get() = quantity * price
}