package com.zagart.museum.shared.strings.di

import com.zagart.museum.shared.strings.StringKey
import com.zagart.museum.shared.strings.StringProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object StringsModule {

    @Provides
    @SettingsKeys
    fun provideSettingsKeys(): List<StringKey> {
        return listOf(
            StringKey(StringProvider.DARK_THEME, Boolean::class),
            StringKey(StringProvider.LANGUAGE, String::class, StringProvider.ENGLISH)
        )
    }
}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class SettingsKeys