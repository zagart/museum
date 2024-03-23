package com.zagart.museum.details.data.di

import com.zagart.museum.details.data.repositories.ArtObjectDetailsRepositoryImpl
import com.zagart.museum.domain.repositories.ArtObjectDetailsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DetailsDataModule {

    @Binds
    abstract fun bindArtObjectDetailsRepository(repository: ArtObjectDetailsRepositoryImpl): ArtObjectDetailsRepository
}