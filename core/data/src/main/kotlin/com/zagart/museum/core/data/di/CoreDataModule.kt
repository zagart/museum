package com.zagart.museum.core.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.zagart.museum.core.data.AppDatabase
import com.zagart.museum.details.data.daos.ArtObjectDetailsDao
import com.zagart.museum.home.data.daos.ArtObjectShortDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
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
    fun provideArtObjectShortDao(database: AppDatabase): ArtObjectShortDao {
        return database.artObjectShortDao()
    }

    @Provides
    fun provideArtObjectDetailsDao(database: AppDatabase): ArtObjectDetailsDao {
        return database.artObjectDetailsDao()
    }

    @Provides
    @Singleton
    @DataStoreSettings
    fun providesDataStoreSettings(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(produceFile = {
            context.preferencesDataStoreFile("settings")
        })
    }
}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class DataStoreSettings