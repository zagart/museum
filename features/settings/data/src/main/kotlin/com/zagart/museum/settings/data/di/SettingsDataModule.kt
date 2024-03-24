package com.zagart.museum.settings.data.di

import com.zagart.museum.settings.data.repositories.SettingsRepositoryImpl
import com.zagart.museum.settings.domain.repositories.SettingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SettingsDataModule {

    @Binds
    abstract fun bindSettingsRepository(repositoryImpl: SettingsRepositoryImpl): SettingsRepository
}