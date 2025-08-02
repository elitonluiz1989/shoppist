package com.elitonluiz1989.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.elitonluiz1989.domain.Item

@Entity(
    tableName = "items",
    indices = [Index(value = ["name"]) ]
)
data class ItemEntity(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var name: String,
    var quantity: Short,
    var price: Int
) {
    fun toItem(): Item = Item(id = id, name = name, quantity = quantity, price = price)

    companion object {
        fun fromItem(item: Item): ItemEntity =
            ItemEntity(id = item.id, name = item.name, quantity = item.quantity, price = item.price)
    }
}