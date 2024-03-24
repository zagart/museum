package com.zagart.museum.home.data.extensions

import com.zagart.museum.api.model.ArtObjectDto
import com.zagart.museum.home.data.models.ArtObjectShortEntity
import com.zagart.museum.home.domain.models.ArtObject

fun List<ArtObjectDto>.dtosAtEntityList(): List<ArtObjectShortEntity> {
    return map {
        ArtObjectShortEntity(
            id = it.id,
            title = it.title,
            objectNumber = it.objectNumber,
            date = System.currentTimeMillis()
        )
    }
}

fun ArtObjectShortEntity.toDomainModel(): ArtObject {
    return ArtObject(
        id = id,
        title = title,
        objectNumber = objectNumber,
        description = null
    )
}