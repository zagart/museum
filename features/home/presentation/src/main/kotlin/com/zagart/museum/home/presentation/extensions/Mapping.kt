package com.zagart.museum.home.presentation.extensions

import com.zagart.museum.home.domain.models.ArtObject
import com.zagart.museum.home.presentation.HomeScreenItem

fun List<ArtObject>.toUiModel(): List<HomeScreenItem> {
    return mapNotNull { HomeScreenItem(it.id, it.title) }
}