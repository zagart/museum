package com.zagart.museum.home.presentation.extensions

import com.zagart.museum.home.domain.models.ArtObject
import com.zagart.museum.home.presentation.HomeScreenImage
import com.zagart.museum.home.presentation.HomeScreenItemModel

fun ArtObject.toUiModel(): HomeScreenItemModel {
    return HomeScreenItemModel(
        id = id,
        title = title,
        objectNumber = objectNumber,
        author = principalOrFirstMaker,
        withAuthorHeader = withAuthorHeader,
        image = image?.let { image ->
            HomeScreenImage(
                url = image.url,
                width = image.width,
                height = image.height
            )
        }
    )
}