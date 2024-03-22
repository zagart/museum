package com.zagart.museum.api.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.zagart.museum.api.MuseumApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object MuseumApiModule {

    private val JSON = Json { ignoreUnknownKeys = true }

    @Provides
    fun provideMuseumApi(): MuseumApi {
        return Retrofit.Builder()
            .baseUrl("https://www.rijksmuseum.nl/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(JSON.asConverterFactory(MediaType.parse("application/json")!!))
            .build()
            .create(MuseumApi::class.java)
    }
}