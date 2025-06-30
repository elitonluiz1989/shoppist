package com.elitonluiz1989.shoppist.items

import com.elitonluiz1989.domain.Item

data class ItemState(
    val items: List<Item> = emptyList(),
    val name: String = "",
    val quantity: String = "",
    val price: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)