package com.zagart.museum.core.data.di

import android.content.Context
import androidx.room.Room
import com.zagart.museum.core.data.AppDatabase
import com.zagart.museum.home.data.daos.ArtObjectDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object CoreDataModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "museum-db").build()
    }

    @Provides
    fun provideArtObjectsDao(database: AppDatabase): ArtObjectDao {
        return database.artObjectDao()
    }
}