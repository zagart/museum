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
import java.lang.ref.WeakReference
import javax.inject.Qualifier
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object TestCoreDataModule {

    private var dataStoreReference: WeakReference<DataStore<Preferences>> = WeakReference(null)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
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
        val currentDataStore = dataStoreReference.get()

        return if (currentDataStore == null) {
            val newDataStore = PreferenceDataStoreFactory.create(produceFile = {
                context.preferencesDataStoreFile("settings")
            })
            dataStoreReference = WeakReference(newDataStore)

            newDataStore
        } else {
            currentDataStore
        }
    }
}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class DataStoreSettings