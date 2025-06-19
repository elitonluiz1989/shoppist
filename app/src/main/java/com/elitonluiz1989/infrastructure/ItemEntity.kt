package com.elitonluiz1989.infrastructure

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.elitonluiz1989.domain.Item

@Entity(
    tableName = "items",
    indices = [Index(value = ["name"], unique = true) ]
)
data class ItemEntity(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var name: String
) {
    fun toItem(): Item = Item(id = id, name = name)

    companion object {
        fun fromItem(item: Item): ItemEntity = ItemEntity(id = item.id, name = item.name)
    }
}