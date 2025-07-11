package com.elitonluiz1989.shoppist.items

import com.elitonluiz1989.domain.Item

sealed class ItemEvent {
    data class Add(val item: Item) : ItemEvent()
    data class Delete(val item: Item) : ItemEvent()
    data class UpdateName(val value: String) : ItemEvent()
    data class UpdateQuantity(val value: String) : ItemEvent()
    data class UpdatePrice(val value: String) : ItemEvent()
    data class UpdateForm(val value: Item) : ItemEvent()
    object Load : ItemEvent()
}