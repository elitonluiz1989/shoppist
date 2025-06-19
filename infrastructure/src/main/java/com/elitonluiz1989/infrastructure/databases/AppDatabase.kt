package com.elitonluiz1989.infrastructure.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import com.elitonluiz1989.infrastructure.daos.ItemsDao
import com.elitonluiz1989.infrastructure.entities.ItemEntity

@Database(entities = [ItemEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun itemsDao(): ItemsDao
}