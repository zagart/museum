package com.zagart.museum.home.data.extensions

import com.zagart.museum.api.model.ArtObjectDto
import com.zagart.museum.home.data.models.ArtObjectShortEntity
import com.zagart.museum.home.domain.models.ArtObject

fun List<ArtObjectDto>.dtosAtEntityList(): List<ArtObjectShortEntity> {
    return map { dto ->
        ArtObjectShortEntity(
            id = dto.id,
            title = dto.title,
            objectNumber = dto.objectNumber,
            date = System.currentTimeMillis(),
            author = dto.principalOrFirstMaker ?: "",
            hasImage = dto.hasImage,
            showImage = dto.showImage,
            imageUrl = dto.webImage?.url ?: ""
        )
    }
}

fun ArtObjectShortEntity.toDomainModel(): ArtObject {
    return ArtObject(
        id = id,
        title = title,
        objectNumber = objectNumber,
        description = null,
        principalOrFirstMaker = author,
        hasImage = hasImage,
        showImage = showImage,
        imageUrl = imageUrl
    )
}