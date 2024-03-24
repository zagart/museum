package com.zagart.museum.details.data.extensions

import com.zagart.museum.api.model.ArtObjectDto
import com.zagart.museum.details.data.models.ArtObjectDetailsEntity
import com.zagart.museum.home.domain.models.ArtObject

fun ArtObjectDto.dtoAsDomainModel(): ArtObject {
    return ArtObject(
        id = id,
        title = title,
        objectNumber = objectNumber,
        description = description,
        principalOrFirstMaker = principalOrFirstMaker ?: "",
        hasImage = hasImage,
        showImage = showImage,
        imageUrl = webImage?.url ?: ""
    )
}

fun ArtObjectDetailsEntity.entityAsDomainModel(): ArtObject {
    return ArtObject(
        id = id,
        title = title,
        objectNumber = objectNumber,
        description = description,
        principalOrFirstMaker = author,
        hasImage = hasImage,
        showImage = showImage,
        imageUrl = imageUrl
    )
}

fun ArtObjectDto.dtoAsEntityModel(): ArtObjectDetailsEntity {
    return ArtObjectDetailsEntity(
        id = id,
        title = title,
        objectNumber = objectNumber,
        description = description ?: "",
        author = principalOrFirstMaker ?: "",
        hasImage = hasImage,
        showImage = showImage,
        imageUrl = webImage?.url ?: ""
    )
}