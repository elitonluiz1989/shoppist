package com.elitonluiz1989.data

import androidx.sqlite.db.SimpleSQLiteQuery
import com.elitonluiz1989.domain.Item
import com.elitonluiz1989.domain.ItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(private val dao: ItemsDao): ItemRepository {
    override fun getAll(itemsToIgnore: List<Long>): Flow<List<Item>> {
        val query = StringBuilder("SELECT * FROM items")

        if (itemsToIgnore.isNotEmpty()) {
            query.append(" WHERE id NOT IN (${itemsToIgnore.joinToString(",")})")
        }

        val sql = SimpleSQLiteQuery(query.toString())

        return dao.getAll(sql).map { list -> list.map { it.toItem() } }
    }

    override suspend fun upsert(model: Item) {
        return dao.upsert(ItemEntity.fromItem(model))
    }

    override suspend fun delete(model: Item) {
        return dao.delete(ItemEntity.fromItem(model))
    }
}