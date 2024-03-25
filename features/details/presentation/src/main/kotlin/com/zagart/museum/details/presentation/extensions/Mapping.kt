package com.zagart.museum.details.presentation.extensions

import com.zagart.museum.details.presentation.models.DetailsImage
import com.zagart.museum.details.presentation.models.DetailsModel
import com.zagart.museum.home.domain.models.ArtObject

fun ArtObject.domainAsUiModel(): DetailsModel {
    return DetailsModel(
        id = id,
        title = title,
        objectNumber = objectNumber,
        description = description ?: "",
        author = principalOrFirstMaker,
        image = image?.let { image ->
            DetailsImage(
                url = image.url,
                height = image.height,
                width = image.width
            )
        }
    )
}