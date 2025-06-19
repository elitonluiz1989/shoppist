package com.elitonluiz1989.domain

import kotlinx.coroutines.flow.Flow

interface ItemRepository {
    fun getAll(): Flow<List<Item>>
    suspend fun upsert(item: Item)
    suspend fun delete(item: Item)
}