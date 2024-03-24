package com.zagart.museum.details.presentation.extensions

import com.zagart.museum.details.presentation.models.DetailsModel
import com.zagart.museum.home.domain.models.ArtObject

fun ArtObject.domainAsUiModel(): DetailsModel {
    return DetailsModel(
        id = id,
        title = title,
        objectNumber = objectNumber,
        description = description ?: "",
        author = principalOrFirstMaker,
        imageUrl = imageUrl
    )
}