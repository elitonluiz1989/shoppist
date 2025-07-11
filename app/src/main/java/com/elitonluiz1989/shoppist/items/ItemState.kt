package com.elitonluiz1989.shoppist.items

import com.elitonluiz1989.domain.Item

data class ItemState(
    val items: List<Item> = emptyList(),
    val id: Long = 0,
    val name: String = "Item",
    val quantity: String = "1",
    val price: String = "0",
    val isLoading: Boolean = false,
    val error: String? = null
)