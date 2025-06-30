package com.elitonluiz1989.data.di

import android.content.Context
import androidx.room.Room
import com.elitonluiz1989.data.AppDatabase
import com.elitonluiz1989.data.ItemsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "shoppist.db"
        ).build()
    }

    @Provides
    fun provideItemsDao(db: AppDatabase): ItemsDao = db.itemsDao()
}