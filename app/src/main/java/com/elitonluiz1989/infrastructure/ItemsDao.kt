package com.elitonluiz1989.infrastructure

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemsDao {
    @Query("select id, name from items")
    fun getAll(): Flow<List<ItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(itemEntity: ItemEntity)

    @Delete
    suspend fun delete(itemEntity: ItemEntity)
}