package com.zagart.museum.home.presentation.extensions

import com.zagart.museum.home.domain.models.ArtObject
import com.zagart.museum.home.presentation.HomeScreenItemModel

fun ArtObject.toUiModel(): HomeScreenItemModel {
    return HomeScreenItemModel(
        id = id,
        title = title,
        objectNumber = objectNumber
    )
}