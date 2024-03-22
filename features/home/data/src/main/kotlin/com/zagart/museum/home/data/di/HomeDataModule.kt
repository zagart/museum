package com.zagart.museum.home.data.di

import com.zagart.museum.home.data.repositories.ArtObjectRepositoryImpl
import com.zagart.museum.home.domain.repositories.ArtObjectRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class HomeDataModule {

    @Binds
    abstract fun bindArtObjectRepository(repository: ArtObjectRepositoryImpl): ArtObjectRepository
}