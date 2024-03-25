package com.zagart.museum.settings.domain.usecases

import com.zagart.museum.shared.strings.StringProvider

object GetLanguageKeysUseCase {

    operator fun invoke(): List<String> {
        return listOf(StringProvider.ENGLISH, StringProvider.DUTCH)
    }
}